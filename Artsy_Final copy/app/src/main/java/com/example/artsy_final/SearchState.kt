//package com.example.artsy_final
//
//sealed class SearchState {
//    object Idle : SearchState()
//    object Loading : SearchState()
//    data class Success(val data: List<Artist>) : SearchState()
//    data class Error(val message: String) : SearchState()
//}