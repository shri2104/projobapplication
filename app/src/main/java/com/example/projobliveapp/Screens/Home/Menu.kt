import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projobliveapp.DataBase.ApiService
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainJobScreenContent(
    onMenuClick: () -> Unit,
    navController: NavHostController,
    userEmail: String,
    apiservice: ApiService
) {
    val scrollState = rememberScrollState()
    val openMenu = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                            contentDescription = "App Logo",
                            modifier = Modifier.size(96.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
                    }
                },
                actions = {
                    IconButton(onClick = {navController.navigate("notificationscreen/$userEmail")}) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notification Icon")
                    }
                }
            )
        }
        ,bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Home, contentDescription = "Home",)
                        Text(text = "Home", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("AvailableInterns/$userEmail")},
                    modifier = Modifier.weight(1f)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Groups, contentDescription = "Internships")
                        Text(text = "Internships", style = MaterialTheme.typography.titleSmall)
                    }
                }
                IconButton(
                    onClick = { navController.navigate("AvailableJobs/$userEmail")},
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
                .verticalScroll(scrollState)
        ) {
            UserGreetingScreen(
                email = userEmail,
                apiService = apiservice
            )

            JobForYou(apiservice,userEmail)
            RecentlyViewedJobsSection()
            ActiveJobsInCitiesSection()
            BrowseByCategorySection()
            TrustedByCompaniesSection(apiservice,userEmail)
            HowItWorksSection()
        }
    }
}

@Composable
fun JobAppMenuContent(
    onCloseMenu: () -> Unit,
    navController: NavHostController,
    userEmail: String,
    apiservice: ApiService
) {
    var userName by remember { mutableStateOf("Loading...") }
    var userlastName by remember { mutableStateOf("Loading...") }
    var userFetchedEmail by remember { mutableStateOf("Loading...") }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val userProfile = apiservice.getProfileDataByEmail(userEmail)
                userName = userProfile.firstName
                userlastName = userProfile.lastName
                userFetchedEmail = userProfile.email
            } catch (e: Exception) {
                userName = "User"
                userFetchedEmail = "Unavailable"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                contentDescription = "App Logo",
                modifier = Modifier.size(86.dp),
                contentScale = ContentScale.Fit
            )
            IconButton(onClick = onCloseMenu) {
                Icon(Icons.Default.Close, contentDescription = "Close Menu", tint = Color.White)
            }
        }
        Column(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text = "$userName $userlastName",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userFetchedEmail,
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }
        Text(
            text = "EXPLORE",
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                MenuItem(icon = Icons.Default.Person, label = "Profile") {
                    navController.navigate("profileSection/$userEmail")
                }
            }
            item {
                MenuItem(icon = Icons.Default.Favorite, label = "Saved Jobs") {
                    navController.navigate("SavedJobs/$userEmail")
                }
            }
            item {
                MenuItem(icon = Icons.Default.Email, label = "Applications") {
                    navController.navigate("Appliedjobs/$userEmail")
                }
            }
            item {
                MenuItem(icon = Icons.Default.WorkOutline, label = "Jobs") {
                    navController.navigate("AvailableJobs/$userEmail")
                }
            }
            item {
                MenuItem(icon = Icons.Default.School, label = "Internships") {
                    navController.navigate("AvailableInterns/$userEmail")
                }
            }
            item {
                MenuItem(icon = Icons.Default.SupportAgent, label = "Help & Support") {
                    navController.navigate(Screen.HelpandSupport.name)
                }
            }
            item {
                MenuItem(icon = Icons.Default.Phone, label = "Contact Us") {
                    navController.navigate(Screen.ContactUsScreen.name)
                }
            }
            item {
                MenuItem(icon = Icons.Default.AddCircleOutline, label = "More") {
                    navController.navigate(Screen.MoreScreen.name)
                }
            }
            item {
                MenuItem(icon = Icons.Default.Logout, label = "Logout") {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.LoginScreen.name)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Version 1.0.0",
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}
