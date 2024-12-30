import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.projobliveapp.Navigation.Screen
import com.example.projobliveapp.R
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainJobScreenContent(
    onMenuClick: () -> Unit,
    navController: NavHostController,
    userEmail: String
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
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Groups, contentDescription = "internship")
                }
                IconButton(
                    onClick = { navController.navigate("AvailableJobs/$userEmail")},
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Work, contentDescription = "Jobs")
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
            SearchBarSection()
            TrendingJobsSection()
            RecentlyViewedJobsSection()
            ActiveJobsInCitiesSection()
            BrowseByCategorySection()
            TrustedByCompaniesSection()
            HowItWorksSection()
        }
    }
}

@Composable
fun JobAppMenuContent(onCloseMenu: () -> Unit, navController: NavHostController, userEmail: String) {
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
            Icon(
                painter = painterResource(id = R.drawable.projob_logo1_12fc55031a756ac453bf),
                contentDescription = "App Logo",
                tint = Color.White,
                modifier = Modifier.size(96.dp)
            )
            IconButton(onClick = onCloseMenu) {
                Icon(Icons.Default.Close, contentDescription = "Close Menu", tint = Color.White)
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            MenuItem(icon = Icons.Default.Person, label = "Profile", onClick = {
                navController.navigate("profileSection/$userEmail")
            })
            MenuItem(icon = Icons.Default.Favorite, label = "Saved Jobs", onClick = {  })
            MenuItem(icon = Icons.Default.Email, label = "Applications", onClick = {  })
            MenuItem(icon = Icons.Default.SupportAgent, label = "Help & Support", onClick = {
                navController.navigate(Screen.HelpandSupport.name)
            })
            MenuItem(icon = Icons.Default.Phone, label = "Contact Us", onClick = {
                navController.navigate(Screen.ContactUsScreen.name)
            })
            MenuItem(icon = Icons.Default.AddCircleOutline, label = "More", onClick = {
                navController.navigate(Screen.MoreScreen.name)
            })
            MenuItem(icon = Icons.Default.Logout, label = "Logout", onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(Screen.LoginScreen.name)
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "Version 1.0.0",
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

