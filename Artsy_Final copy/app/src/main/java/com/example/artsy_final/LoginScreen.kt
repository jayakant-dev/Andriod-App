package com.example.artsy_final
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController){
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Login")
                } ,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        var text by remember { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var hasEmailFocus by remember { mutableStateOf(false) }
        var hasPasswordFocus by remember { mutableStateOf(false) }

        var isMailError by remember { mutableStateOf(false) }
        var errorMailMessage by remember { mutableStateOf("") }
        var isPassError by remember { mutableStateOf(false) }
        var errorPassMessage by remember { mutableStateOf("") }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp)
            ) {

                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it
                        if (hasEmailFocus && text.isNotEmpty()) {
                            isMailError = false
                            errorMailMessage = ""
                        }},
                    label = { Text("Email") },
                    isError = isMailError,
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                hasEmailFocus = true
                                if (text.isEmpty()) {
                                    isMailError = true
                                    errorMailMessage = "Email cannot be empty"
                                }
                            } else if (hasEmailFocus) {
                                // Only validate on focus loss if the user had focused before
                                if (text.isEmpty()) {
                                    isMailError = true
                                    errorMailMessage = "Email cannot be empty"
                                } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                                    isMailError = true
                                    errorMailMessage = "Invalid email format"
                                } else {
                                    isMailError = false
                                    errorMailMessage = ""
                                }
                                hasEmailFocus = false
                            }
                        }.fillMaxWidth(),

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                    supportingText = {
                        if (isMailError) {
                            Text(
                                text = errorMailMessage,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )


                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it
                        if (hasPasswordFocus && password.isNotEmpty()) {
                            isPassError = false
                            errorPassMessage = ""
                        }},
                    isError = isPassError,
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                hasPasswordFocus = true
                                if (password.isEmpty()) {

                                    isPassError = true
                                    errorPassMessage = "Password cannot be empty"
                                }
                            } else if (hasPasswordFocus) {
                                // Only validate on focus loss if the user had focused before
                                if (password.isEmpty()) {
                                    isPassError = true
                                    errorPassMessage = "Password cannot be empty"
                                }  else {
                                    isPassError = false
                                    errorPassMessage = ""
                                }
                                hasPasswordFocus = false
                            }
                        }.fillMaxWidth(),
                    supportingText = {
                        if(isPassError){
                            Text(text = errorPassMessage,
                                color = MaterialTheme.colorScheme.error)
                        }
                    }

                )

                var loading by remember { mutableStateOf(false) }
                var invalid by remember { mutableStateOf(false) }
                val context = LocalContext.current

                Button(
                    onClick = {
                        loading = true
                        invalid = false
                        val call = RetrofitInstance.api.login(LoginRequest(text, password))
                        call.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                loading = false
                                if (response.isSuccessful) {
                                    val loginResponse = response.body()
                                    if (loginResponse?.success == true){
                                        val userName = loginResponse.user?.name
                                        val userEmail = loginResponse.user?.email
                                        val userID = loginResponse.user?.gravitarID
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Login successful")
                                        }
                                        UserManager.saveUser(context, userName, userEmail, userID)
                                        navController.navigate("home")
                                    }
                                    else{
                                         invalid = true
                                    }
                                    loading = false

                                } else {
                                    invalid = true
                                    println("Login failed: ${response.errorBody()?.string()}")
                                }
                            }
                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                loading = false
                                invalid = true
                                println("Network error: ${t.message}")
                            }
                        })
                    },
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth()) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 8.dp)
                        )
                    } else {
                        Text("Login")
                    }

                }

                if(invalid){
                    Text("Username or password is incorrect",
                        color = MaterialTheme.colorScheme.error)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Don't have an account yet? ")
                    TextButton(onClick = { navController.navigate("register") }) {
                        Text(text = "Register")
                    }
                }

            }
        }

    }
}


