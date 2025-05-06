package com.example.artsy_final

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(navController: NavController) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    val name = UserManager.userName
    val id = UserManager.userID
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            title = { Text("Artist Search") },

            actions = {
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
                if(name != null && id != null){
                    AvatarWithDropdownMenu(id = id.toString(), navController = navController,
                        snackbarHostState = snackbarHostState )
                }

                else{
                    IconButton(onClick = {navController.navigate("login")}) {
                        Icon(Icons.Default.Person,contentDescription = "User")
                    }
                }

            }
        )
    },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            Text(
                text = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
                modifier = Modifier.padding(19.dp)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Favourites",
                    modifier = Modifier.padding(19.dp)
                )

                Button(onClick = { navController.navigate("login")}) {
                    Text("Log in to see favorites")
                }


                Text(
                    text = "Powered by Artsy",
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.artsy.net/")
                    }
                )
            }

        }
    }
}


@Composable
fun AvatarWithDropdownMenu(id: String, navController: NavController, snackbarHostState: SnackbarHostState) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    // Avatar Button
    AsyncImage(
        model = "https://gravatar.com/avatar/${id}",
        contentDescription = "User Avatar",
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable { expanded = true }
    )

    // Dropdown Menu
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Log Out") },
            onClick = {
                expanded = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Log Out successful")
                }
                CookieJarManager.clearCookies()
                UserManager.clearUser(context)
                println("Logged out")
            }
        )
        DropdownMenuItem(
            text = { Text( color = Color.Red,
                    text = "Delete Account" )},
            onClick = {
                expanded = false
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Account successfully deleted")
                }
                CookieJarManager.clearCookies()
                UserManager.clearUser(context)
                println("Account deleted")
            }
        )
    }
}
