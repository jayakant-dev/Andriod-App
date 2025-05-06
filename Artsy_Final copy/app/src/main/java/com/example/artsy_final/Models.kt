package com.example.artsy_final

import com.google.gson.annotations.SerializedName

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val fname: String, val email: String, val password: String)


data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: UserInfo?
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: UserInfo?
)

data class Artist(
    @SerializedName("ID") val id: String,
    @SerializedName("Name") val name: String,
    @SerializedName("ImageURL") val imageUrl: String
)

data class ArtistDetails(
    @SerializedName("Name") val name: String,
    @SerializedName("Nationality") val nationality: String,
    @SerializedName("Bday") val bday: String,
    @SerializedName("Dday") val dday: String,
    @SerializedName("Bio") val bio: String,
)

data class ArtistArtworks(
    @SerializedName("ID") val id: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Date") val date: String,
    @SerializedName("ImageSrc") val imgsrc: String,
)

data class ArtistGene(
    @SerializedName("Name") val name: String,
    @SerializedName("ImageSrc") val imgsrc: String,
)


data class UserInfo(
    val name: String,
    val email: String,
    val gravitarID: String
)

