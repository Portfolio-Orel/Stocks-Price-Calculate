package com.orels.stock_price

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.orels.presentation.theme.StockPriceTheme
import com.orels.presentation.ui.ActivityResultCallbackManager
import com.orels.presentation.ui.LocalActivityResultCallbackManager
import com.orels.presentation.ui.LoginViewModel


val LocalFacebookCallbackManager =
    staticCompositionLocalOf { error("No CallbackManager provided") }


class MainActivity : ComponentActivity() {
    private var callbackManager = ActivityResultCallbackManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            StockPriceTheme {
                CompositionLocalProvider(
                    LocalActivityResultCallbackManager provides callbackManager
                ) {
                    LoginScreen()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        println()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!callbackManager.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val callbackManager = LocalActivityResultCallbackManager.current
    DisposableEffect(Unit) {
        callbackManager.addListener(viewModel)
        onDispose {
            callbackManager.removeListener(viewModel)
        }
    }
    val context = LocalContext.current
    Column {
        AndroidView(
            factory = { context ->
                // Inflate your XML layout using the LayoutInflater
                return@AndroidView LayoutInflater.from(context)
                    .inflate(com.orels.presentation.R.layout.facebook_login_button, null)
            },
            update = { view ->
                // Perform any necessary setup or modifications to the view
                // For example, you can set listeners or update the view's properties

                // Access specific views within your layout
                val button =
                    view.findViewById<LoginButton>(com.orels.presentation.R.id.login_button)
//                button.setPermissions(listOf("email"))
                val callbackManager = CallbackManager.Factory.create()
                button.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        println()
                    }

                    override fun onCancel() {
                        println()
                    }

                    override fun onError(exception: FacebookException) {
                        println()
                    }
                })
                button.setOnClickListener {
                    viewModel.login(context as? Activity)
                }
            }
        )
        Button(onClick = viewModel::test) {
            Text(text = "Test")
        }
    }

}