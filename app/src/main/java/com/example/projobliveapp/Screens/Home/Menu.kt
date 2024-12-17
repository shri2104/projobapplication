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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projobliveapp.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainJobScreenContent(onMenuClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val openMenu = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Job App", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
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
                    Icon(Icons.Default.Check, contentDescription = "Applied Jobs")
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Profile")
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon( Icons.Default.List, contentDescription = "list")
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
fun JobAppMenuContent(onCloseMenu: () -> Unit) {
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
            MenuItem(icon = Icons.Default.Person, label = "Profile", onClick = {  })
            MenuItem(icon = Icons.Default.Favorite, label = "Saved Jobs", onClick = {  })
            MenuItem(icon = Icons.Default.Email, label = "Applications", onClick = {  })
            MenuItem(icon = Icons.Default.SupportAgent, label = "Help & Support", onClick = { })
            MenuItem(icon = Icons.Default.Phone, label = "Contact Us", onClick = { })
            MenuItem(icon = Icons.Default.Logout, label = "Logout", onClick = {  })
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

