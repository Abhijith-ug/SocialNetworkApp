package com.abhijith.socialnetworkapp.featurepost.presentation.createpost

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.R
import com.abhijith.socialnetworkapp.core.domain.states.StandardTextFieldState
import com.abhijith.socialnetworkapp.core.presentation.components.StandardScaffold
import com.abhijith.socialnetworkapp.core.presentation.components.StandardTextField
import com.abhijith.socialnetworkapp.core.presentation.components.StandardToolbar
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceSmall
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable

fun CreatePostScreen(
            navController: NavController,
            viewModel: CreatePostViewModel = hiltViewModel()
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){

        viewModel.onEvent(CreatePostEvent.PickImage(it))

    }

    Column(
                modifier = Modifier.fillMaxSize()
            ) {
                StandardToolbar(
                    navController = navController,
                    showBackArrow = true,
                    title = {
                        Text(
                            text = stringResource(id = R.string.create_a_new_post),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SpaceLarge)
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(16f / 9f)
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onBackground,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                               galleryLauncher.launch("image/*")
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.choose_image),
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    StandardTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = viewModel.descriptionState.value.text,
                        hint = stringResource(id = R.string.description),
                        error = "",
                        singleLine = false,
                        maxLines = 5,
                        onValueChange = {
                            viewModel.onEvent(
                                CreatePostEvent.EnterDescription(it)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(SpaceLarge))
                    Button(
                        onClick = {
                                  viewModel.onEvent(CreatePostEvent.PostImage)
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = stringResource(id = R.string.post),
                            color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.width(SpaceSmall))
                        Icon(imageVector = Icons.Default.Send, contentDescription = null)
                    }
                }
            }
        }

