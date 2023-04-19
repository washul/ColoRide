package com.wsl.coloride.screens.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wsl.domain.model.Route
import com.wsl.domain.model.UserType


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RouteCard(route: Route = Route(), onClickItem: (Route) -> Unit) {

    Card(elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClickItem(route) }) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            //Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                GlideImage(
                    model = route.people.find { it.userType == UserType.OWNER }?.image,
                    contentDescription = "User image",
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp)
                )
                Spacer(modifier = Modifier.width(32.dp))
                Column(Modifier.fillMaxWidth()) {
                    // title
                    Text(
                        text = route.title, style = MaterialTheme.typography.h6, color = Color.Black
                    )

                    // Subtitle
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = route.owner.userName,
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                }
            }

            //Body
            Row(
                Modifier
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = route.departureArrival.second.image,
                    contentDescription = "Arrival",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }

            Row(Modifier.padding(start = 16.dp, end = 24.dp, top = 16.dp)) {

                // Description
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = route.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Bottom
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                Box(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {

                    // Icon Buttons like/share
                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                Icons.Default.Favorite, contentDescription = null, tint = Color.Gray
                            )
                        }

                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}