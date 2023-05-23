package com.abhijith.socialnetworkapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.abhijith.socialnetworkapp.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StandardTextField(
    modifier: Modifier =Modifier,
    text: String,
    hint: String,
    maxLenth:Int = 40,
    error: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    showPasswordToggle:Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    ) {
    var isPasswordToggleDisplayed by remember {
        mutableStateOf(keyboardType == KeyboardType.Password)
    }
    Column(modifier = Modifier.fillMaxWidth()) {

        TextField(
            value = text,
            onValueChange ={if(it.length<maxLenth){
                onValueChange(it)
            } } ,
            placeholder = {
                Text(text = hint, style = MaterialTheme.typography.body1)
            },
            isError = error!="",
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (!showPasswordToggle&&isPasswordToggleDisplayed){
                PasswordVisualTransformation()
            }
            else{
                VisualTransformation.None
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            trailingIcon = {
                if (isPasswordToggleDisplayed) {
                    IconButton(onClick = {onPasswordToggleClick(showPasswordToggle)}) {
                        Icon(
                            imageVector = if (showPasswordToggle) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            }, contentDescription = if (showPasswordToggle){
                                stringResource(id = R.string.password_visible_content_description)
                            }
                            else{
                                stringResource(id = R.string.password_hidden_content_description)
                            }
                        )

                    }
                }
            }
        )
        if (error.isNotEmpty()){
            Text(text = error,
            style = MaterialTheme.typography.body2
            , color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
        }

    }



}

