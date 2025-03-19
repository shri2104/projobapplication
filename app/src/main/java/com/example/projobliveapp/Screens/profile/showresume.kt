import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.projobliveapp.DataBase.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

suspend fun downloadResume(
    context: Context,
    apiService: ApiService,
    userId: String,
    onResult: (Boolean, String) -> Unit
) {
    try {
        val response: Response<ResponseBody> = apiService.downloadResume(userId)

        if (response.isSuccessful && response.body() != null) {
            val fileName = "resume_$userId.pdf"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                withContext(Dispatchers.IO) {
                    uri?.let {
                        resolver.openOutputStream(it)?.use { outputStream ->
                            response.body()?.byteStream()?.copyTo(outputStream)
                        }
                    }
                }

                onResult(true, "Downloaded to: Downloads folder")
            } else {
                val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

                withContext(Dispatchers.IO) {
                    val inputStream: InputStream? = response.body()?.byteStream()
                    val outputStream: OutputStream = FileOutputStream(file)

                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                }

                onResult(true, "Downloaded to: ${file.absolutePath}")
            }
        } else {
            onResult(false, "Failed to download")
        }
    } catch (e: Exception) {
        Log.e("DownloadError", "Error: ${e.message}")
        onResult(false, "Error: ${e.message}")
    }
}

@Composable
fun DownloadScreen(apiService: ApiService, userEmail: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var fetchedUserId by remember { mutableStateOf<String?>(null) }

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        ActivityCompat.requestPermissions(
            context as android.app.Activity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
    }

    LaunchedEffect(userEmail) {
        if (userEmail.isNotEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                fetchedUserId = userIdResponse.userId
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching user ID: ${e.message}", e)
                errorMessage = "Error fetching user ID: ${e.message}"
                Toast.makeText(context, "Error fetching user ID: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    val userId = fetchedUserId ?: ""

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userId.isNotEmpty()) {
            Text(
                text = "User ID: $userId",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (userId.isNotEmpty()) {
                    coroutineScope.launch {
                        downloadResume(context, apiService, userId) { success, resultMessage ->
                            message = resultMessage
                            Toast.makeText(context, resultMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "User ID not available", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = userId.isNotEmpty()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Download Resume")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
        }

        if (message.isNotEmpty()) {
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}


@Preview
@Composable
fun PreviewDownloadScreen() {
    // Pass a mock implementation of ApiService for preview (optional)
}
