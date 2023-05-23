package com.abhijith.socialnetworkapp.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhijith.socialnetworkapp.R
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.presentation.ui.theme.SpaceMedium


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge, end = SpaceLarge,
                top = SpaceLarge, bottom = 50.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = viewModel.usernameText.value,
                hint = stringResource(id = R.string.usernamehint),
                onValueChange = {
                    viewModel.setUserNameText(it)
                }, error = viewModel.usernameError.value
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = viewModel.passwordText.value,
                hint = stringResource(id = R.string.passwordhint),
                onValueChange = {
                    viewModel.setPasswordText(it)
                }, keyboardType = KeyboardType.Password,
                showPasswordToggle = viewModel.showPassword.value,
                onPasswordToggleClick = {
                    viewModel.setShowPassword(it)
                }, error = viewModel.passwordError.value
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(onClick = { }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth().height(50.dp)
            ) {
                Text(text = stringResource(id = R.string.login)
                , color = MaterialTheme.colors.onPrimary)

            }

        }
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(signUpText)
                }
            }, style = MaterialTheme.typography.body1,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}