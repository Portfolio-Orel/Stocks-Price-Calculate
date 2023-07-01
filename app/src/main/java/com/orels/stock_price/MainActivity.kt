package com.orels.stock_price

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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
    Column(modifier = Modifier.fillMaxSize()) {
        if (viewModel.state is LoginViewModel.LoginState.LoggedIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
                    .padding(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
            ) {
                Icon(Icons.Rounded.Share, contentDescription = "Share", modifier = Modifier.size(40.dp).clickable {
                    viewModel.share()
                }, tint = if(viewModel.isShared) Color.Cyan else Color.White)
                viewModel.friendsList?.forEach {
                    Text(text = it.name ?: "Hi")
                }
                if (viewModel.profileUrl != null) {
                    AsyncImage(
                        modifier = Modifier.size(100.dp),
                        model = viewModel.profileUrl,
                        contentDescription = null,
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    (0..4).forEach { _ ->
                        // Star icon
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            Text(text = "Logged in")

        } else {
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

                    button.setOnClickListener {
//                    viewModel.login(context as? Activity)
                    }
                }
            )
        }
    }

}