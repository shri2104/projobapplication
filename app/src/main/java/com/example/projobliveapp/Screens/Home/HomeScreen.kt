import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projobliveapp.R

class JobSearchHomePageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobSearchHomePage()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobSearchHomePage() {
    val scrollState = rememberScrollState()
    val openMenu = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Dashboard", style = MaterialTheme.typography.headlineSmall) },
                actions = {
                    IconButton(onClick = { openMenu.value = true }) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile Icon"
                        )
                    }
                    DropdownMenu(
                        expanded = openMenu.value,
                        onDismissRequest = { openMenu.value = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                // Handle navigation to Applied Jobs
                                openMenu.value = false // Close the menu
                            },
                            text = { Text("Applied Jobs") } // Correctly pass the Text here
                        )
                        DropdownMenuItem(
                            onClick = {
                                // Handle navigation to Profile
                                openMenu.value = false // Close the menu
                            },
                            text = { Text("Profile") } // Correctly pass the Text here
                        )
                    }



                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            SearchBarSection()
            TrustedByCompaniesSection()
            BrowseByCategorySection()
        }
    }
}

@Composable
fun SearchBarSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Search Jobs") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("City") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Search Action */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
    }
}

@Composable
fun TrustedByCompaniesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Trusted by Companies",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(10) { // Replace with the actual list of companies
                Image(
                    painter = painterResource(id = R.drawable.microsoft), // Replace with actual company logo drawable
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun BrowseByCategorySection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Browse by Category",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CategoryCard(category = "Development")
            CategoryCard(category = "Management")
            CategoryCard(category = "Health & Care")
        }
    }
}

@Composable
fun CategoryCard(category: String) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp),
        elevation = CardDefaults.cardElevation(4.dp) // Set elevation for the card
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFBBDEFB)), // Set background color here
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun JobSearchHomePagePreview() {
    JobSearchHomePage()
}
