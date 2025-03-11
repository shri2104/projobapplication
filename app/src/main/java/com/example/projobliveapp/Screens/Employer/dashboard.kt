import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobPostingScreen(navController: NavController, userEmail: String, apiService: ApiService) {
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var employerid by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    LaunchedEffect(userEmail) {
        if (!userEmail.isNullOrEmpty()) {
            isLoading = true
            errorMessage = null
            try {
                val userIdResponse = apiService.getuserid(userEmail)
                employerid = userIdResponse.userId
            } catch (e: Exception) {
                errorMessage = "Error fetching personal data: ${e.message}"
                Toast.makeText(context, "Error fetching personal data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showBottomSheet.value = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notification Icon")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Assignment, contentDescription = "Application")
                        Text(text = "Applications", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("postedjobs/${employerid}/${userEmail}")},
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Work, contentDescription = "Jobs")
                        Text(text = "Jobs", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Message, contentDescription = "Messages")
                        Text(text = "Messages", style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Direct Job Posting",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Post your opening and start receiving the applications. Be featured in top search results.",
                style = MaterialTheme.typography.bodyMedium
            )

            // Bullet points section
            Text(text = "Benefits of posting a job:")
            BulletPointText(text = "Reach a large pool of candidates.")
            BulletPointText(text = "Save time and resources with automated processes.")
            BulletPointText(text = "Get featured in top search results to attract more attention.")
            BulletPointText(text = "Easy to manage and track applications.")
            BulletPointText(text = "Access to detailed analytics for better decision-making.")
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { navController.navigate("jobpost/${employerid}/${userEmail}")},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(Color.Blue)
                ) {
                    Text("Post a Job")
                }
                Button(
                    onClick = {navController.navigate("postedjobs/${employerid}/${userEmail}") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE6F7FF)),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.weight(1f) // Ensures equal width
                ) {
                    Text(
                        text = "Posted Jobs",
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Bottom Sheet Content
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet.value = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = { showBottomSheet.value = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }

                Text(text = "Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Divider()

                TextButton(onClick = {navController.navigate("CompanyProfileScreen/$userEmail")  }) {
                    Text("View Profile", style = MaterialTheme.typography.bodyLarge)
                }

                TextButton(onClick = { FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.LoginScreen.name) }) {
                    Text("Logout", style = MaterialTheme.typography.bodyLarge, color = Color.Red)
                }
            }
        }
    }
}


@Composable
fun BulletPointText(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "•", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}
