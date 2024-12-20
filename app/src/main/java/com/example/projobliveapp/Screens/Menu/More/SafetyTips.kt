import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProJobSafetyTipsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Safety Tips") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color.Gray), RoundedCornerShape(8.dp))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large Picture Section
                Image(
                    painter = painterResource(id = R.drawable.shield), // Replace with your drawable
                    contentDescription = "Safety Icon",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                )

                // Safety Tips Section
                Text(
                    text = "Safety Tips",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )

                Text(
                    text = "1. Never share your personal details with unknown sources.\n" +
                            "2. Verify the company and job details before applying.\n" +
                            "3. Avoid paying any money for job offers.\n" +
                            "4. Be cautious of unsolicited job offers via email or social media.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Small Centered Info Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(LightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "At ProJob, we are committed to making your online experience a safe and reliable one.\n" +
                                "The following information is designed to help internship/job seekers identify common red flags and avoid fraud.",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text("We are here to help", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(Modifier.height(10.dp))
                Text("If you have encountered ")
                Spacer(Modifier.height(5.dp))
                Text("any such suspicious activity, please let us")
                Text(
                    " know via our Help center.",
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PagePreview() {
    val navController = rememberNavController()
    ProJobSafetyTipsScreen(navController = navController)
}
