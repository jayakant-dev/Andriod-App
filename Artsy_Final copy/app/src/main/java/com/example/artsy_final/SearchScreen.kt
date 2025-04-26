//package com.example.artsy_final
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material.Text
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.SearchBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//

package com.example.artsy_final
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController){
    Column{
        Text(
            text = "LOL Screen",
            modifier = Modifier.padding(19.dp)
        )
    }

}

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchScreen(navController: NavController) {
//    val viewModel: SearchViewModel = viewModel()
//    val searchState by viewModel.searchState.collectAsState()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        SearchBar(
//            query = viewModel.searchQuery,
//            onQueryChange = { viewModel.searchQuery = it },
//            onSearch = { viewModel.performSearch() },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            when (searchState) {
//                is SearchState.Loading -> {
//                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//                }
//                is SearchState.Success -> {
//                    val results = (searchState as SearchState.Success).data
//                    ArtistResultsList(results = results, navController = navController)
//                }
//                is SearchState.Error -> {
//                    Text(
//                        text = "Error: ${(searchState as SearchState.Error).message}",
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//                else -> {}
//            }
//        }
//    }
//}
//
//@Composable
//fun SearchBar(
//    query: String,
//    onQueryChange: (String) -> Unit,
//    onSearch: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var active by remember { mutableStateOf(false) }
//
//    androidx.compose.material3.SearchBar(
//        query = query,
//        onQueryChange = onQueryChange,
//        onSearch = {
//            onSearch()
//            active = false
//        },
//        active = active,
//        onActiveChange = { active = it },
//        modifier = modifier
//    ) {
//        // Additional search suggestions can go here
//    }
//}
//
//@Composable
//fun ArtistResultsList(results: List<Artist>, navController: NavController) {
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(results) { artist ->
//            ArtistCard(
//                artist = artist,
//                onClick = {
//                    navController.navigate("details/${artist.id}")
//                }
//            )
//        }
//    }
//}
//
//@Composable
//fun ArtistCard(artist: Artist, onClick: () -> Unit) {
//    TODO("Not yet implemented")
//}
//
