package com.dave.githubsearchcompose.view.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dave.githubsearchcompose.enums.TextFieldState
import com.dave.githubsearchcompose.ui.theme.Gray
import com.dave.githubsearchcompose.ui.theme.green_500
import com.dave.githubsearchcompose.ui.theme.grey_100
import com.dave.githubsearchcompose.ui.theme.grey_50
import com.dave.githubsearchcompose.ui.theme.grey_700
import com.dave.githubsearchcompose.ui.theme.grey_900
import com.dave.githubsearchcompose.ui.theme.red_500
import com.dave.githubsearchcompose.ui.theme.White

@Composable
fun FocusBorderEditText(
    value: String = "",
    state: TextFieldState,
    modifier: Modifier = Modifier,
    onChangeText: (String) -> Unit = {},
    @StringRes placeHolder: Int = -1,
    maxLength: Int = -1,
    keyboardType: KeyboardType = KeyboardType.Text,
    @StringRes headerTitle: Int = -1,
    @StringRes errorMessage: Int = -1,
    hint: String? = null
) {

    Box {
        OutlinedTextField(
            modifier = modifier
                .height(52.dp),
            value = value,
            onValueChange = { newValue ->
                onChangeText(
                    if (maxLength > -1 && newValue.length > maxLength) newValue.take(maxLength) else newValue
                )
            },
            textStyle = TextStyle(color = if (state == TextFieldState.READONLY) Gray else grey_700),
            placeholder = {
                Text(
                    modifier = Modifier.defaultMinSize(0.dp, 0.dp),
                    text = stringResource(id = placeHolder),
                    fontSize = 14.sp,
                    color = grey_100,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false))
                )
            },
            isError = state == TextFieldState.ERROR,
            enabled = state != TextFieldState.READONLY,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                focusedIndicatorColor = when (state) {
                    TextFieldState.ACTIVE -> grey_900
                    TextFieldState.CONFIRM -> green_500
                    else -> grey_900
                },
                focusedPlaceholderColor = grey_100,
                unfocusedContainerColor = White,
                unfocusedIndicatorColor = when (state) {
                    TextFieldState.CONFIRM -> green_500
                    else -> grey_100
                },
                unfocusedPlaceholderColor = grey_100,
                errorContainerColor = White,
                errorIndicatorColor = red_500,
                disabledContainerColor = grey_50,
                disabledIndicatorColor = grey_100
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}
