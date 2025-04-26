//package com.example.artsy_final
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class SearchViewModel : ViewModel() {
//    var searchQuery by mutableStateOf("")
//        private set
//
//    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
//    val searchState: StateFlow<SearchState> = _searchState
//
//    fun performSearch() {
//        viewModelScope.launch {
//            _searchState.value = SearchState.Loading
//            try {
//                val response = RetrofitInstance.api.searchArtists(searchQuery)
//                _searchState.value = SearchState.Success(response)
//            } catch (e: Exception) {
//                _searchState.value = SearchState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
//}