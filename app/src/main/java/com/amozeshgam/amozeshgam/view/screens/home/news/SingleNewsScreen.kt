package com.amozeshgam.amozeshgam.view.screens.home.news

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetPost
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.handler.exoplayerHandler.view.VideoPlayer
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.news.SingleNewsViewModel

@Composable
fun ViewSingleNews(viewModel: SingleNewsViewModel = hiltViewModel(), navController: NavController) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val liked = remember {
        mutableStateOf(false)
    }
    val id = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }
    val addPostToFavoriteLoading = remember {
        mutableStateOf(false)
    }
    val postData = remember {
        mutableStateOf<ApiResponseGetPost?>(null)
    }
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current

    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        worker = {
            postData.value = viewModel.getPostData(id).await()
            liked.value = postData.value?.isLike ?: false
            isLoading.value = false
            true
        },
        ifForShowContent = postData.value != null,
        onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UiHandler.AnythingRow(
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        navController.popBackStack()
                    },
                itemOne = {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "پست ها",
                        color = Color.White,
                        fontFamily =AmozeshgamTheme.fonts["regular"],
                        fontSize = 20.sp

                    )
                }, itemTwo = {
                    Icon(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.White
                    )
                })
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                if (postData.value!!.media.endsWith(".mp4")) {
                    VideoPlayer(
                        modifier = Modifier.fillMaxSize(),
                        videoLink = postData.value!!.media
                    ) {

                    }
                } else if (postData.value!!.media.endsWith(".gif")) {
                    val imageLoader = ImageLoader.Builder(context).components {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }.build()
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = postData.value!!.media,
                        contentDescription = null,
                        imageLoader = imageLoader
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = postData.value!!.media,
                        contentDescription = null
                    )
                }
            }
            UiHandler.AnythingRow(
                itemOne = {
                    IconButton(onClick = {
                        viewModel.createLink(NavigationScreenHandler.SingleNewsScreen.route + "/" + id.toString())
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                itemTwo = {
                    IconButton(onClick = {
                        if (!addPostToFavoriteLoading.value && !liked.value) {
                            viewModel.addPostToFavorite(id = id)
                            addPostToFavoriteLoading.value = true
                        }
                    }) {
                        if (addPostToFavoriteLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = AmozeshgamTheme.colors["primary"]!!
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = if (liked.value) R.drawable.ic_like_fill else R.drawable.ic_like),
                                contentDescription = null,
                                tint = if (liked.value) Color.Red else Color.White
                            )
                        }
                    }
                })
            UiHandler.AnythingRow(
                modifier = Modifier.padding(10.dp),
                itemOne = {
                    Button(
                        modifier = Modifier
                            .background(Color.Black)
                            .border(2.dp, Color.White, RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        onClick = { /*TODO*/ }) {
                        Text(text = "مشاهده", color = Color.White)
                    }
                }, itemTwo = {
                    UiHandler.AnythingRow(modifier = Modifier.padding(10.dp), itemOne = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.widthIn(max = 100.dp),
                                text = postData.value!!.name,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Right

                            )
                            Text(
                                modifier = Modifier.widthIn(max = 100.dp),
                                text = postData.value!!.role,
                                fontSize = 8.sp,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Right
                            )
                        }
                    }, itemTwo = {
                        AsyncImage(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(100)),
                            model = postData.value!!.avatar,
                            placeholder = painterResource(id = R.drawable.ic_app_logo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    })
                }
            )
        }
        LaunchedEffect(Unit) {
            viewModel.addPostToFavorite.observe(lifecycle) {
                addPostToFavoriteLoading.value = false
                if (it) {
                    liked.value = true
                    Toast.makeText(context, "با موفقیت ثبت شد", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "خطا", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}