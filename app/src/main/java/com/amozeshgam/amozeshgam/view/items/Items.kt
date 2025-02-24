package com.amozeshgam.amozeshgam.view.items

import android.icu.text.DecimalFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme

@Composable
fun StoryItem(onClick: () -> Unit, imageUrl: String, text: String = "") {
    Column {
        Card(
            modifier = Modifier
                .padding(5.dp)
                .size(65.dp)
                .border(
                    3.dp, Brush.sweepGradient(
                        colors = listOf(
                            colorResource(id = R.color.whiteBorderStory),
                            colorResource(id = R.color.primaryColor)
                        ), center = Offset.Infinite
                    ), RoundedCornerShape(100.dp)
                ), shape = RoundedCornerShape(100), onClick = onClick

        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
                    .clip(RoundedCornerShape(100)),
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

        }
        Text(
            modifier = Modifier
                .width(60.dp)
                .align(Alignment.CenterHorizontally),
            text = text,
            color = AmozeshgamTheme.colors["textColor"]!!,
            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MyCourseItem(
    courseImage: String, courseName: String, onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(AmozeshgamTheme.colors["itemColor"]!!)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onClick) {
                Icon(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = null
                )
            }
            UiHandler.AnythingRow(itemOne = {
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = courseName)
            }, itemTwo = {
                AsyncImage(
                    modifier = Modifier.aspectRatio(16f / 9f),
                    model = courseImage,
                    contentDescription = null
                )
            })
        }
    }
}

@Composable
fun FieldAndSubFieldItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    text: String = "",
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .size(180.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!
            ),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(20.dp)
                .weight(0.80f)
                .align(Alignment.CenterHorizontally),
            model = imageUrl,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(0.20f)
                .padding(2.dp)
                .align(Alignment.CenterHorizontally),
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = AmozeshgamTheme.colors["textColor"]!!,
            fontFamily = FontFamily(Font(R.font.yekan_bakh_bold))
        )
    }
}

@Composable
fun ActiveDevicesItem(
    deviceName: String, deviceModel: String, deviceIP: String, onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(AmozeshgamTheme.colors["itemColor"]!!)
        .clickable {
            onClick()
        }) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = deviceName,
                fontSize = 22.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            Text(
                text = deviceModel,
                fontSize = 17.sp,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            Text(
                text = deviceIP,
                fontSize = 12.sp,
                color = AmozeshgamTheme.colors["secondaryTextColor"]!!
            )
        }
    }
}

@Composable
fun PaymentItem(imageUrl: String = "", text: String = "") {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(165.dp)
            .height(230.dp),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!)
    ) {
        AsyncImage(modifier = Modifier.weight(0.80f), model = imageUrl, contentDescription = null)
        Text(
            modifier = Modifier
                .weight(0.20f)
                .padding(2.dp)
                .align(Alignment.CenterHorizontally),
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = AmozeshgamTheme.colors["textColor"]!!,
            fontFamily = FontFamily(Font(R.font.yekan_bakh_semibold))
        )
    }
}

@Composable
fun OrderReviewItem(name: String, image: String) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(110.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            modifier = Modifier.weight(1f),
            text = name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = AmozeshgamTheme.colors["textColor"]!!,
            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
        )

        AsyncImage(
            modifier = Modifier.aspectRatio(10f / 8f), model = image, contentDescription = null
        )
    }
}

@Composable
fun CartItem(
    image: String,
    price: String,
    name: String,
    removeLoading: Boolean,
    onRemoveClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(110.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (removeLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                } else {
                    IconButton(onClick = onRemoveClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["errorColor"]!!
                        )
                    }
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Text(
                text = DecimalFormat(",000").format(price.toInt()),
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
        }
        AsyncImage(
            modifier = Modifier.aspectRatio(10f / 8f), model = image, contentDescription = null
        )
    }
}

@Composable
fun CourseItem(
    imageUrl: String, packageName: String, time: String, teacher: String, onClick: () -> Unit = {},

    ) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(165.dp)
            .height(160.dp)
            .shadow(
                10.dp,
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(10.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .padding(5.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    ), model = imageUrl, contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                text = packageName,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                UiHandler.AnythingRow(itemOne = {
                    Image(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null
                    )

                }, itemTwo = {
                    Text(
                        text = time, style = TextStyle(
                            fontSize = 12.sp, color = AmozeshgamTheme.colors["textColor"]!!
                        )
                    )
                })
                UiHandler.AnythingRow(itemOne = {
                    Image(
                        modifier = Modifier.size(12.dp),
                        painter = painterResource(id = R.drawable.ic_person),
                        contentDescription = null
                    )

                }, itemTwo = {
                    Text(
                        text = teacher, style = TextStyle(
                            fontSize = 12.sp, color = AmozeshgamTheme.colors["textColor"]!!
                        )
                    )
                })
            }
        }
    }

}


@Composable
fun PodcastItem(
    onClick: () -> Unit = {}, imageUrl: String, text: String, speech: String,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(158.dp)
            .height(235.dp)
            .shadow(
                7.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onClick
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp)), model = imageUrl, contentDescription = null
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                maxLines = 3,
                color = AmozeshgamTheme.colors["textColor"]!!,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_semibold))
            )

            UiHandler.AnythingRow(modifier = Modifier.fillMaxWidth(), itemOne = {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = null
                )
            }, itemTwo = {
                Text(
                    text = speech,
                    fontSize = 10.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            })
        }
    }

}

@Composable
fun RoadMapAbilityItem(image: String, text: String, itemNumber: Int) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.9f)
                .padding(5.dp)
                .shadow(
                    5.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                    ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = AmozeshgamTheme.colors["background"]!!
            ),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = text,
                    fontFamily = AmozeshgamTheme.fonts["regular"],
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    overflow = TextOverflow.Ellipsis
                )
                AsyncImage(
                    modifier = Modifier.size(50.dp),
                    model = image,
                    contentDescription = null
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.1f)
                .padding(5.dp)
                .shadow(
                    5.dp,
                    shape = RoundedCornerShape(10.dp),
                    spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                    ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                ),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = AmozeshgamTheme.colors["background"]!!
            ),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = itemNumber.toString(),
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"]!!
                )
            }
        }
    }
}

@Composable
fun RoadMapSoftwareCanBuild(modifier: Modifier = Modifier,image: String, text: String) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .width(80.dp)
            .height(100.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = AmozeshgamTheme.colors["background"]!!
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(2.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = image,
                contentDescription = null
            )
            Text(
                text = text,
                fontFamily = AmozeshgamTheme.fonts["regular"],
                color = AmozeshgamTheme.colors["textColor"]!!,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InformationItem(image: Int, title: String, text: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(200.dp)
            .height(135.dp)
            .shadow(
                10.dp,
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .size(50.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                tint = AmozeshgamTheme.colors["primary"]!!
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            }
            Text(text = text, color = AmozeshgamTheme.colors["textColor"]!!)
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun InformationPackageItem(image: String, name: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(190.dp)
            .height(170.dp)
            .shadow(
                10.dp,
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            model = image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.End),
            text = name,
            textAlign = TextAlign.Right,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
            color = AmozeshgamTheme.colors["textColor"]!!
        )
    }
}

@Composable
fun InformationTeamItem(image: String, name: String, duty: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(210.dp)
            .height(180.dp)
            .shadow(
                10.dp,
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(10.dp)
                    .size(80.dp),
                model = image,
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_bold)),
                color = AmozeshgamTheme.colors["textColor"]!!
            )
            Text(
                text = duty,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                color = AmozeshgamTheme.colors["textColor"]!!
            )
        }
    }
}

@Composable
fun CommentItem(avatarLink: String, username: String, date: String, body: String) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = AmozeshgamTheme.colors["background"]!!
        ), elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.padding(10.dp), text = date)
            UiHandler.AnythingRow(itemOne = {
                Text(text = username, color = AmozeshgamTheme.colors["textColor"]!!)
            }, itemTwo = {
                AsyncImage(
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .clip(RoundedCornerShape(100)),
                    model = avatarLink,
                    contentDescription = ""
                )
            })
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), text = body, style = TextStyle(
                textAlign = TextAlign.Right, color = AmozeshgamTheme.colors["textColor"]!!
            )
        )
    }
}


@Composable
fun NotificationItem(title: String, body: String, date: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(AmozeshgamTheme.colors["background"]!!)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Text(
                    text = body,
                    fontSize = 17.sp,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UiHandler.AnythingRow(modifier = Modifier.clickable(onClick = onClick), itemOne = {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = AmozeshgamTheme.colors["primary"]!!
                    )
                }, itemTwo = {
                    Text(
                        text = "جزییات بیشتر", color = AmozeshgamTheme.colors["primary"]!!
                    )
                })
                Text(text = date, color = AmozeshgamTheme.colors["secondaryTextColor"]!!)
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(AmozeshgamTheme.colors["primary"]!!)
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .padding(5.dp),
                painter = painterResource(id = R.drawable.ic_notification_message),
                contentDescription = null,
                tint = AmozeshgamTheme.colors["itemColor"]!!
            )
        }
    }
}

@Composable
fun UserChatItem(message: String, date: String) {
    val screenSize = LocalConfiguration.current
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        UiHandler.AnythingRow(modifier = Modifier
            .padding(10.dp)
            .align(Alignment.CenterEnd),
            itemOne = {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = date,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            },
            itemTwo = {
                Card(
                    modifier = Modifier.widthIn(max = (screenSize.screenWidthDp / 2).dp),
                    shape = RoundedCornerShape(
                        topStart = 10.dp, topEnd = 10.dp, bottomStart = 10.dp
                    ),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
                ) {
                    Box {
                        Text(
                            modifier = Modifier
                                .padding(15.dp)
                                .align(Alignment.Center),
                            text = message,
                            color = Color.White,
                            fontSize = 17.sp,
                            textAlign = TextAlign.End
                        )
                    }
                }
            })
    }
}

@Composable
fun AdminChatItem(message: String, date: String) {
    val screenSize = LocalConfiguration.current
    Box(modifier = Modifier.fillMaxWidth()) {
        UiHandler.AnythingRow(modifier = Modifier.padding(10.dp), itemOne = {
            Card(
                modifier = Modifier.widthIn(max = (screenSize.screenWidthDp / 2).dp),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
            ) {
                Box {
                    Text(
                        modifier = Modifier
                            .padding(15.dp)
                            .align(Alignment.Center),
                        text = message,
                        color = Color.White,
                        fontSize = 17.sp,
                        textAlign = TextAlign.End
                    )
                }
            }
        }, itemTwo = {
            Text(
                modifier = Modifier.padding(2.dp),
                text = date,
                color = AmozeshgamTheme.colors["textColor"]!!
            )
        })
    }
}

@Composable
fun OrdersItem(
    orderDate: String,
    orderType: String,
    payment: String,
    orderNumber: String,
    trackNumber: String,
    status: Boolean,
    onReviewClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(325.dp)
            .border(
                1.dp, AmozeshgamTheme.colors["secondaryColor"]!!, RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.End
        ) {
            Text(
                text = orderNumber,
                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = orderDate, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":تاریخ شفارش",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = orderType, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":نوع سفارش", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onReviewClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
                ) {
                    Text(
                        text = "لیست سفارشات",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                    )
                }
                Text(
                    text = ":دوره های این سفارش",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = "${DecimalFormat(",000").format(payment.toInt())}تومان",
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                    )
                }
                Text(
                    text = ":مبلغ", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = trackNumber, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":شماره پیگیری", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (status) {
                    Box(
                        modifier = Modifier
                            .background(
                                AmozeshgamTheme.colors["errorColor"]!!,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .height(70.dp)
                            .padding(horizontal = 5.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(5.dp),
                            text = "تکمیل شده",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(
                                AmozeshgamTheme.colors["errorColor"]!!,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .height(70.dp)
                            .padding(horizontal = 5.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.Center),
                            text = "ناموفق",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                        )
                    }
                }

                Text(
                    text = ":وضعیت", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
        }
    }
}


@Composable
fun TransactionItem(
    transactionDate: String,
    transactionType: String,
    payment: String,
    trackNumber: String,
    transactionNumber: String,
    status: Boolean,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(280.dp)
            .border(
                1.dp, AmozeshgamTheme.colors["secondaryColor"]!!, RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), horizontalAlignment = Alignment.End
        ) {
            Text(
                text = transactionNumber,
                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transactionDate, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":تاریخ شفارش", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = transactionType, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":نوع سفارش", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Text(
                        text = "${DecimalFormat(",000").format(payment.toInt())}تومان",
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                    )
                }
                Text(
                    text = ":مبلغ", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = trackNumber, color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Text(
                    text = ":شماره پیگیری", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (status) {
                    Text(
                        modifier = Modifier
                            .background(
                                Color.Green,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        text = "تکمیل شده",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                    )
                } else {
                    Text(
                        modifier = Modifier
                            .background(
                                AmozeshgamTheme.colors["errorColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clip(RoundedCornerShape(10.dp))
                            .padding(10.dp),
                        text = "ناموفق",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                    )
                }
                Text(
                    text = "وضعیت", color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
        }
    }
}

@Composable
fun JobItem(title: String, imageUrl: String, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(80.dp)
            .shadow(
                7.dp,
                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(7.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(0.15f)
                    .height(30.dp),
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = AmozeshgamTheme.colors["textColor"]!!
            )
            Row(
                modifier = Modifier.weight(0.85f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.padding(10.dp), text = title, style = TextStyle(
                        color = AmozeshgamTheme.colors["textColor"]!!,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(16f / 9f),
                    model = imageUrl,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun FavoritesItem(
    image: String, title: String, onViewClick: () -> Unit, onRemoveClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(110.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onRemoveClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_like_fill),
                        contentDescription = null,
                        tint = AmozeshgamTheme.colors["errorColor"]!!
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
            }
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .width(130.dp)
                    .height(50.dp),
                onClick = onViewClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmozeshgamTheme.colors["primary"]!!,
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "مشاهده دوره", color = Color.White)
            }
        }
        AsyncImage(
            modifier = Modifier.aspectRatio(10f / 8f),
            model = image,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_app_banner_day)
        )
    }
}

@Composable
fun OptionItem(
    text: String, tag: Int, currentTag: Int, onClick: (tag: Int) -> Unit,
) {
    Card(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .height(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = AmozeshgamTheme.colors["itemColor"]!!
        ),
        border = if (tag == currentTag) BorderStroke(
            width = 1.dp, color = AmozeshgamTheme.colors["primary"]!!
        ) else null,
        shape = RoundedCornerShape(10.dp),
        onClick = {
            onClick(tag)
        }) {
        Row(
            modifier = Modifier
                .padding(vertical = 15.dp, horizontal = 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                color = AmozeshgamTheme.colors[if (tag == currentTag) "primary" else "yellowColor"]!!,
            )
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(100))
                    .background(AmozeshgamTheme.colors[if (tag == currentTag) "primary" else "yellowColor"]!!)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = (tag + 1).toString(),
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
            }
        }
    }
}


@Composable
fun MyRoadMapItem(
    roadMapImage: String, roadMapName: String, percentRoadmap: Float, onClick: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onClick) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = roadMapName,
                        color = Color.White,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                    AsyncImage(
                        modifier = Modifier.size(40.dp),
                        model = roadMapImage,
                        contentDescription = null
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${percentRoadmap}%",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"]
                )
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    LinearProgressIndicator(modifier = Modifier
                        .padding(5.dp)
                        .weight(1f),
                        color = AmozeshgamTheme.colors["primary"]!!,
                        progress = { (percentRoadmap / 100).toFloat() })
                }
                Text(
                    text = "درصد پیشرفت",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = AmozeshgamTheme.fonts["regular"]
                )
            }
        }
    }
}

@Composable
fun RoadMapExamItem(modifier: Modifier = Modifier, title: String, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AmozeshgamTheme.colors["borderColor"]!!,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = null
            )
            Text(
                text = title,
                fontFamily = AmozeshgamTheme.fonts["bold"],
                color = AmozeshgamTheme.colors["textColor"]!!
            )
        }
    }
}
