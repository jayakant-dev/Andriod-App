//package com.example.artsy_final

package com.example.artsy_final

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    // Controls expansion state of the search bar
    var query by remember { mutableStateOf("") }
    var selectedArtist by remember { mutableStateOf("") }

    var artists by remember { mutableStateOf<List<Artist>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var artistDetails by remember { mutableStateOf<ArtistDetails?>(null)}
    var artistArtworks by remember { mutableStateOf<List<ArtistArtworks>?>(null)}
    var artistGene by remember { mutableStateOf<List<ArtistGene>?>(null)}

    LaunchedEffect(query) {
        if (query.length > 3) {
            isLoading = true
            try {

                artists = RetrofitInstance.api.searchName(q = query)
                println(artists)
                //artistDetails = RetrofitInstance.api.getDetails(artistId = "4d8b928b4eb68a1b2c0001f2")
//                artistArtworks = RetrofitInstance.api.getArtworks(artistId = "4d8b928b4eb68a1b2c0001f2")
//                artistGene = RetrofitInstance.api.getCategory(artworkId="515b0f9338ad2d78ca000554")
//                Log.d("ArtistsResponse", artists.toString())
//                // Or print each artist more clearly:
//                artists.forEach { artist ->
//                    Log.d("ArtistsResponse", "${artist.id}, ${artist.name}, ${artist.imageUrl}")
//                }
            } catch (e: Exception) {
                artists = emptyList()
            } finally {
                isLoading = false
            }
        } else {
            artists = emptyList()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {query = it},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = { },
                    placeholder = { Text("Search Artist...") }
                )
            },
            expanded = true,
            onExpandedChange = {},
        ) {
            if (artists.isNotEmpty()) {
                println("YESS")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(artists) { artist ->
                        ArtistCard(artist){
                                artistId -> selectedArtist = artistId
                                navController.navigate("ArtistDetails")
                        }
                    }
                }
            }

        }

    }
}

    @Composable
    fun ArtistCard(artist: Artist, onClick: (String) -> Unit) {
        Card(
            onClick = {onClick(artist.id)},
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                AsyncImage(
                    model = artist.imageUrl,
                    contentDescription = "Artist Image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = artist.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }




