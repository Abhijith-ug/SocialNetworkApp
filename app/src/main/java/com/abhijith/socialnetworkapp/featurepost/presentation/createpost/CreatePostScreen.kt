package com.abhijith.socialnetworkapp.featurepost.presentation.createpost

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abhijith.socialnetworkapp.core.presentation.components.StandardScaffold
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun CreatePostScreen(navController: NavController) {
    var description by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(border = BorderStroke(1.dp, color = Color.Black))
        ) {
            IconButton(onClick ={},
            modifier = Modifier.size(150.dp).align(Alignment.Center)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "" )

            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text("Description") }
        )

        Button(
            onClick = {
                // Handle post creation
            }, modifier = Modifier.align(alignment = Alignment.End)
        ) {
            Text(text = "Post")
        }
        }


    }


