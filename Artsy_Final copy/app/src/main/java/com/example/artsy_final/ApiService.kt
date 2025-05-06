package com.example.artsy_final

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @GET("api/search")
    suspend fun searchName(@Query("q") q: String): List<Artist>

    @GET("api/artist/{artistId}")
    suspend fun getDetails(@Path("artistId") artistId: String): ArtistDetails

    @GET("api/artworks/{artistId}")
    suspend fun getArtworks(@Path("artistId") artistId: String): List<ArtistArtworks>

    @GET("api/categories/{artwork_id}")
    suspend fun getCategory(@Path("artwork_id") artworkId: String): List<ArtistGene>


//    app.use('/api/artworks', artworksRoutes); - artistId
//    app.use('/api/categories', categoryRoutes); - artwork_id
//    app.use('/delete', authorizationRoutes)
//    app.use('/api/favorites', favoritesRoutes);
}

