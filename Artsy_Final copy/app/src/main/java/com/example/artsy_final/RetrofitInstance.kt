package com.example.artsy_final

import android.content.Context
import androidx.core.content.edit
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080"

    lateinit var api: ApiService
        private set

    fun initialize(context: Context) {
        // Initialize CookieJar first
        CookieJarManager.initialize(context)

        // Build OkHttpClient with the persistent cookie jar
        val okHttpClient = OkHttpClient.Builder()
            .cookieJar(CookieJarManager.getCookieJar())
            .build()

        // Create Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }
}


object CookieJarManager {
    private var cookieJar: PersistentCookieJar? = null

    fun initialize(context: Context) {
        if (cookieJar == null) {
            cookieJar = PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(context.applicationContext)
            )
        }
    }

    fun getCookieJar(): PersistentCookieJar {
        return cookieJar ?: throw IllegalStateException("CookieJar not initialized. Call CookieJarManager.initialize() first.")
    }

    fun clearCookies() {
        cookieJar?.clear()
    }
}

object UserManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_ID = "id"

    private var userName: String? = null
    private var userEmail: String? = null
    private var userID: String? = null

    fun saveUser(context: Context, name: String?, email: String?, id: String?) {
        userName = name
        userEmail = email
        userID = id

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_NAME, name)
            putString(KEY_EMAIL, email)
            putString(KEY_ID, id)
            apply()
        }
    }

    fun loadUser(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        userName = prefs.getString(KEY_NAME, null)
        userEmail = prefs.getString(KEY_EMAIL, null)
        userID = prefs.getString(KEY_ID, null)
    }

    fun getUserName(): String? = userName
    fun getUserEmail(): String? = userEmail
    fun getUserID(): String? = userID

    fun clearUser(context: Context) {
        userName = null
        userEmail = null
        userID = null
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit() { clear() }
    }
}
