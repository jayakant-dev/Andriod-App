package com.example.artsy_final
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artsy_final.ui.theme.Artsy_FinalTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserManager.loadUser(applicationContext)
        RetrofitInstance.initialize(applicationContext)
        setContent {
            Artsy_FinalTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        Greeting( navController = navController)
                    }
                    composable("search") {
                        SearchScreen(navController = navController)
                    }
                    composable("login") {
                        LoginScreen(navController = navController)
                    }
                    composable("register") {
                        RegisterScreen(navController = navController)
                    }

                }
            }
        }
    }
}




//    Column {
//        Text(
//            text = "Hello $name!",
//            modifier = modifier
//        )
//        Icon(Icons.Default.Search, contentDescription = "Search")
//        Icon(Icons.Default.Person, contentDescription = "User")
//        Icon(Icons.Default.Close, contentDescription = "Close")
//        Icon(Icons.Default.Info, contentDescription = "Details Tab")
//        Icon(Icons.Default.AccountBox, contentDescription = "Artworks Tab")
//        Icon(Icons.Default.PersonSearch, contentDescription = "Similar Tab")
//        Icon(Icons.Outlined.StarBorder, contentDescription = "Star Border")
//        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Right Arrow")
//        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//    }



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Artsy_FinalTheme {
//        Greeting()
//    }
//}