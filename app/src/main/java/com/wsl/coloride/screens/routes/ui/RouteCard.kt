package com.wsl.coloride.screens.detail.ui

import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.wsl.coloride.R
import com.wsl.coloride.ui.theme.Secundary
import com.wsl.domain.model.Route
import com.wsl.domain.model.UserType
import com.wsl.utils.extentions.hasMidStar
import com.wsl.utils.extentions.showHourAsString
import com.wsl.utils.extentions.showTodayTomorrow


@Composable
fun RouteCard(route: Route = Route(), onClickItem: (Route) -> Unit) {

    val maxHeight = 230.dp
    val minHeight = 180.dp

    var isSmallSize by rememberSaveable { mutableStateOf(true) }
    val size by animateDpAsState(targetValue = if (isSmallSize) minHeight else maxHeight)

    Card(elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height(size)
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isSmallSize = !isSmallSize }) {

        Row {
            //Left
            LeftSide(route = route, modifier = Modifier.height(maxHeight))

            //Right
            //Close
            RightSideOnClose(route = route, modifier = Modifier.height(maxHeight))
            //Open
            RightSideOnOpen(
                route = route,
                isSmallSize = isSmallSize,
                modifier = Modifier.padding(8.dp)
            )

        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RightSideOnOpen(route: Route, isSmallSize: Boolean, modifier: Modifier) {
    if (!isSmallSize)
        Row(modifier = modifier) {
            route.people.forEach { user ->
                GlideImage(
                    model = user.image,
                    contentDescription = "User image",
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp)
                )
            }
        }
}

@Composable
fun RightSideOnClose(route: Route, modifier: Modifier) {
    ConstraintLayout(modifier = modifier.padding(8.dp)) {

        val (textTitle, textDescription, rowButtons) = createRefs()

        // title
        Text(
            text = route.title,
            style = MaterialTheme.typography.h6,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(textTitle) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        // Description
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = route.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2,
                color = Color.Gray,
                textAlign = TextAlign.Justify,
                modifier = Modifier.constrainAs(textDescription) {
                    top.linkTo(textTitle.bottom, margin = 12.dp)
                    start.linkTo(textTitle.start)
                    end.linkTo(textTitle.end)
                }
            )
        }

        //Buttons
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

            // Icon Buttons like/share
            Row(
                modifier = Modifier
                    .constrainAs(rowButtons) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LeftSide(route: Route, modifier: Modifier) {
    ConstraintLayout(
        modifier
            .width(100.dp)
            .background(Secundary)
    ) {

        val (glideProfileImage, rowRating, textDate, textTime) = createRefs()

        GlideImage(
            model = route.people.find { it.userType == UserType.OWNER }?.image,
            contentDescription = "User image",
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(60.dp)
                .constrainAs(glideProfileImage) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        Row(modifier = Modifier
            .constrainAs(rowRating) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(glideProfileImage.bottom, margin = 4.dp)
            }) {

            CreateUserRating(route)

        }

        // Subtitle
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {

            //Date
            Text(
                text = route.date.showTodayTomorrow(),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textDate) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(textTime.top)
                    }
            )

            //Time
            Text(
                text = route.date.showHourAsString(),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(textTime) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                    }
            )

        }

    }
}

@Composable
fun CreateUserRating(route: Route) {
    //put full and half stars
    val rating = route.owner.rating.asOwner
    repeat(rating.toInt()) {
        Icon(
            painter = painterResource(id = R.drawable.complete_star),
            contentDescription = "Rating",
            tint = Color.Yellow,
            modifier = Modifier.size(15.dp),
        )
        Spacer(modifier = Modifier.width(5.dp))

        if (it == rating.toInt()-1 && route.owner.rating.asOwner.hasMidStar()) {
            Icon(
                painter = painterResource(id = R.drawable.half_star),
                contentDescription = "Rating",
                tint = Color.Yellow,
                modifier = Modifier.size(15.dp),
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }

    //put empty stars
    repeat((rating.toInt()..5).count()) {
        Icon(
            painter = painterResource(id = R.drawable.empty_star),
            contentDescription = "Rating",
            tint = Color.LightGray,
            modifier = Modifier.size(15.dp),
        )
        Spacer(modifier = Modifier.width(5.dp))
    }
}
