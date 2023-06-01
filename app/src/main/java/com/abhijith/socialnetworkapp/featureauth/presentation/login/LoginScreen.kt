package com.abhijith.socialnetworkapp.featureauth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhijith.socialnetworkapp.R
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.util.UiEvent
import com.abhijith.socialnetworkapp.core.util.Screen
import com.abhijith.socialnetworkapp.featureauth.util.AuthError
import com.abhijith.socialnetworkapp.core.presentation.util.asString
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val state = viewModel.loginState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true ){
        viewModel.eventFlow.collectLatest {
            event ->
            when(event){
                is UiEvent.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.Navigate ->{
                    navController.navigate(event.route)
                }
            }
        }
    }
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
                text = emailState.text,
                hint = stringResource(id = R.string.usernamehint),
                keyboardType = KeyboardType.Email,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredEmail(it))
                }, error = when(emailState.error){
                    is AuthError.FieldEmpty -> stringResource(id = R.string.this_field_cant_be_empty)
                    else -> ""
                }
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            StandardTextField(
                text = passwordState.text,
                hint = stringResource(id = R.string.passwordhint),
                onValueChange = {
                    viewModel.onEvent(LoginEvent.EnteredPassword(it))
                }, keyboardType = KeyboardType.Password,
                showPasswordToggle = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                }, error = when(passwordState.error){
                    is AuthError.FieldEmpty -> stringResource(id = R.string.this_field_cant_be_empty )
                    else -> ""
                }
            )
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(onClick = {
                            viewModel.onEvent(LoginEvent.Login)
            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(50.dp)
            ) {
                Text(text = stringResource(id = R.string.login)
                , color = MaterialTheme.colors.onPrimary)

            }
            if (state.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
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
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clickable {
                    navController.navigate(
                        Screen.RegisterScreen.route
                    )
                }
        )
    }

}