//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.compose.*
//import androidx.compose.foundation.background
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreen() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = "Home", fontWeight = FontWeight.Bold) },
//                backgroundColor = Color.White,
//                contentColor = Color.Black,
//                elevation = 4.dp
//            )
//        },
//        bottomBar = {
//            BottomAppBar(
//                backgroundColor = Color.White,
//                elevation = 8.dp
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = { /* TODO: Add navigation logic */ }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_home),
//                            contentDescription = "Home",
//                            tint = Color.Black
//                        )
//                    }
//                    IconButton(onClick = { /* TODO: Add navigation logic */ }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_search),
//                            contentDescription = "Search",
//                            tint = Color.Black
//                        )
//                    }
//                    IconButton(onClick = { /* TODO: Add navigation logic */ }) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.ic_profile),
//                            contentDescription = "Profile",
//                            tint = Color.Black
//                        )
//                    }
//                }
//            }
//        }
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(innerPadding)
//                .fillMaxSize()
//                .background(Color(0xFFF8F9FA)),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            contentPadding = PaddingValues(16.dp)
//        ) {
//            items(10) { index ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp),
//                    shape = RoundedCornerShape(8.dp),
//                    elevation = 4.dp
//                ) {
//                    Box(modifier = Modifier.fillMaxSize()) {
//                        Image(
//                            painter = painterResource(id = R.drawable.sample_image),
//                            contentDescription = "Card Image",
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                        Text(
//                            text = "Card Item $index",
//                            color = Color.White,
//                            fontSize = 18.sp,
//                            fontWeight = FontWeight.Bold,
//                            modifier = Modifier
//                                .align(Alignment.BottomStart)
//                                .padding(8.dp)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
