package com.orels.presentation.ui.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.orels.presentation.R

/**
 * @author Orel Zilberman
 * 06/10/2022
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Input(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    title: String = "",
    placeholder: String = "",
    minLines: Int = 1,
    maxLines: Int = 1,
    maxCharacters: Int = -1,
    isError: Boolean = false,
    isPassword: Boolean = false,
    shouldFocus: Boolean = false,
    leadingIcon: @Composable (() -> Unit) = { },
    trailingIcon: @Composable ((tint: Color) -> Unit) = { },
    trailingIconColors: IconColors = IconColors(
        emptyTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
        filledTextColor = MaterialTheme.colorScheme.onBackground,
    ),
    onTextChange: (String) -> Unit = {}
) {
    var value by remember { mutableStateOf("") }
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val lineHeight = 40
    val minLineWidth = 40
    val characterWidth = 30
    val focusRequester = FocusRequester()

    var inputModifier = if (shouldFocus) Modifier.focusRequester(focusRequester) else Modifier

    inputModifier =
        if (maxLines == 1) inputModifier else inputModifier.height((lineHeight * minLines).dp)

    inputModifier =
        if (maxCharacters == -1) inputModifier else inputModifier.width((minLineWidth + characterWidth * maxCharacters).dp)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(title)
        Box(modifier = Modifier.zIndex(1f)) {
            OutlinedTextField(
                modifier = inputModifier
                    .fillMaxWidth()
                    .focusable(enabled = shouldFocus),
                value = transformString(textStyle = textStyle, text = value),
                onValueChange = {
                    value = it
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                },
                singleLine = maxLines == 1,
                visualTransformation = if (isPassword && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else keyboardOptions,
                trailingIcon = {
                    if (isPassword) {
                        PasswordIcon(
                            passwordVisible = passwordVisible.value,
                            onClick = { passwordVisible.value = !passwordVisible.value })
                    } else {
                        trailingIcon(if (value.isEmpty()) trailingIconColors.emptyTextColor else trailingIconColors.filledTextColor)
                    }
                },
                isError = isError,
                keyboardActions = keyboardActions,
            )
        }
    }
}

private fun transformString(textStyle: TextStyle, text: String): String =
    when (textStyle) {
        TextStyle.Default -> text
        TextStyle.AllCaps -> text.uppercase()
    }


@Composable
private fun PasswordIcon(
    passwordVisible: Boolean,
    onClick: () -> Unit
) {
    val image = if (passwordVisible)
        painterResource(id = R.drawable.ic_visibility_off_24)
    else painterResource(id = R.drawable.ic_visibility_24)
    val description =
        if (passwordVisible) stringResource(R.string.hide_password) else stringResource(R.string.show_password)

    IconButton(onClick = onClick) {
        Icon(painter = image, description)
    }
}

enum class TextStyle {
    Default,
    AllCaps
}

data class IconColors(
    val emptyTextColor: Color = Color.Unspecified,
    val filledTextColor: Color = Color.Unspecified,
)