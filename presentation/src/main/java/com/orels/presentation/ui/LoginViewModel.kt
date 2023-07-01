package com.orels.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), ActivityResultCallbackI {
    sealed class LoginState {
        object Initial : LoginState()
        object Processing : LoginState()
        data class Success(val loginResult: LoginResult) : LoginState()
        data class Error(val exception: FacebookException) : LoginState()
    }

    private var auth: FirebaseAuth = Firebase.auth

    private var callbackManager = CallbackManager.Factory.create()
    var state by mutableStateOf<LoginState>(LoginState.Initial)
        private set

    init {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    state = LoginState.Success(loginResult)
                }

                override fun onCancel() {
                    state = LoginState.Initial
                }

                override fun onError(exception: FacebookException) {
                    state = LoginState.Error(exception)
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        callbackManager.onActivityResult(requestCode, resultCode, data)

    fun login(activity: Activity?) {
        state = LoginState.Processing
        activity?.let {
            LoginManager.getInstance()
                .logInWithReadPermissions(it, listOf("public_profile"))
        }
    }

    fun test() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { _, response ->
            val jsonObject = response?.jsonObject
            val id = jsonObject?.getString("id")
            val name = jsonObject?.getString("name")

            val pictureData = jsonObject?.getJSONObject("picture")
            val pictureUrl = pictureData?.getJSONObject("data")?.getString("url")

            println("ID: $id")
            println("Name: $name")
            println("Profile Picture URL: $pictureUrl")
        }
        val parameters  = Bundle()
        parameters.putString("fields", "id,name,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun handleFacebookAccessToken(token: AccessToken, activity: Activity) {

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(
                    activity,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

    }
}