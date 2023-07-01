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
import com.facebook.GraphResponse
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(), ActivityResultCallbackI {
    sealed class LoginState {
        object Initial : LoginState()
        object Processing : LoginState()
        object LoggedOut : LoginState()
        object LoggedIn : LoginState()
        data class Error(val exception: FacebookException) : LoginState()
    }

    private var auth: FirebaseAuth = Firebase.auth

    var profileUrl by mutableStateOf<String?>(null)
        private set
    var friendsList by mutableStateOf<List<Friend>?>(null)
        private set

    var isShared by mutableStateOf(false)
        private set

    var callbackManager = CallbackManager.Factory.create()
    var state by mutableStateOf<LoginState>(LoginState.Initial)
        private set

    init {
        checkAuthState()
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    checkAuthState()
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

    fun checkAuthState() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        state = if (isLoggedIn) {
            getProfileUrl()
            getFriendsList()
            LoginState.LoggedIn
        } else {
            LoginState.LoggedOut
        }
    }

    fun share() {
        isShared = !isShared
    }

    override fun onCleared() {
        super.onCleared()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        callbackManager.onActivityResult(requestCode, resultCode, data)


    fun getFriendsList() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newGraphPathRequest(
            accessToken,
            "/me/friends"
        ) { response: GraphResponse? ->
            val jsonObject = response?.jsonObject
            friendsList = parseFriendList(jsonObject)
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun parseFriendList(jsonObject: JSONObject?): List<Friend> {
        val friendList = mutableListOf<Friend>()

        val friendsData = jsonObject?.optJSONArray("data")
        if (friendsData != null) {
            for (i in 0 until friendsData.length()) {
                val friendObject = friendsData.optJSONObject(i)
                val id = friendObject?.optString("id")
                val name = friendObject?.optString("name")
                val pictureData = friendObject?.optJSONObject("picture")?.optJSONObject("data")
                val pictureUrl = pictureData?.optString("url")
                friendList.add(Friend(id, name, pictureUrl))
            }
        }

        return friendList
    }

    data class Friend(
        val id: String?,
        val name: String?,
        val pictureUrl: String?
    )

    fun getProfileUrl() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { _, response ->
            val jsonObject = response?.jsonObject
            val pictureData = jsonObject?.getJSONObject("picture")
            val pictureUrl = pictureData?.getJSONObject("data")?.getString("url")

            profileUrl = pictureUrl
        }
        val parameters = Bundle()
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