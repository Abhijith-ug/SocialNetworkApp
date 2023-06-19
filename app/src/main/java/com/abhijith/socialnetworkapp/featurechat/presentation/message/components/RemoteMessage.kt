package com.abhijith.socialnetworkapp.featurechat.presentation.message.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceLarge
import com.abhijith.socialnetworkapp.core.presentation.ui.theme.SpaceMedium

@Composable
fun RemoteMessage(
    message:String,
    formattedTime:String,
    modifier: Modifier = Modifier,
    color:Color = Color.DarkGray,
    triangleWidth: Dp = 30.dp,
    triangleHeight:Dp =  30.dp,
){
    val cornerRadius = MaterialTheme.shapes.medium.bottomStart
    Row(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .weight(1f)
            .background(
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(SpaceMedium)
            .drawBehind {
                val cornerRadiusPx = cornerRadius.toPx(
                    shapeSize = size,
                    density = Density(
                        density
                    )
                )
                val path = Path().apply {
                    moveTo(
                        0f,
                        size.height - cornerRadiusPx
                    )
                    lineTo(0f, size.height + triangleHeight.toPx())
                    lineTo(triangleWidth.toPx(), size.height - cornerRadiusPx)
                    close()
                }
                drawPath(
                    path = path,
                    color = color
                )
            }){
            Text(text = message)
        }
        Spacer(modifier = Modifier.width(SpaceLarge))
        Text(text = formattedTime, color = MaterialTheme.colors.onSurface,
        modifier = Modifier.align(Alignment.Bottom))

    }

}