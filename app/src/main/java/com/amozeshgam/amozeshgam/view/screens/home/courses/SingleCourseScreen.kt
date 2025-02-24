package com.amozeshgam.amozeshgam.view.screens.home.courses

import android.icu.text.DecimalFormat
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourse
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourseComment
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourseCourseData
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseGetCourseSeasons
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.RemoteStateHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.CommentItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.course.SingleCourseViewModel
import com.smarttoolfactory.ratingbar.RatingBar
import com.smarttoolfactory.ratingbar.model.GestureStrategy
import com.smarttoolfactory.ratingbar.model.RateChangeStrategy
import com.smarttoolfactory.ratingbar.model.RatingInterval

@Composable
fun ViewSingleCourse(
    viewModel: SingleCourseViewModel = hiltViewModel(), navController: NavController,
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val courseData = remember {
        mutableStateOf<ApiResponseGetCourse?>(null)
    }

    val tabs = remember {
        arrayOf("نظرات", "مشخصات دوره")
    }
    val courseId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }
    val liked = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = courseData.value != null,
        worker = {
            courseData.value = viewModel.getCourseData(courseId = courseId).await()
            liked.value = courseData.value?.course?.like ?: false
            isLoading.value = false
            true
        }, onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }) {
        AmozeshgamTheme(
            darkTheme = UiHandler.themeState()
        ) {
            UiHandler.ContentWithScaffold(title = "", onBackButtonClick = {
                navController.popBackStack()
            }, navigationIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        viewModel.saveLinkToClipBoard("https://app.amozeshgam.com/course/${courseId}")
                        navController.navigate(NavigationScreenHandler.MyCartScreen.route)
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                    IconButton(onClick = {

                        Toast.makeText(context, "کپی شد", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(R.drawable.ic_share),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }

                    IconButton(onClick = {
                        if (!liked.value) {
                            viewModel.addCourseToFavorites(courseId = courseId)
                        }
                    }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(if (liked.value) R.drawable.ic_like_fill else R.drawable.ic_like),
                            contentDescription = null,
                            tint = if (liked.value) AmozeshgamTheme.colors["errorColor"]!! else AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                }
            }) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .aspectRatio(16f / 9f),
                        colors = CardDefaults.cardColors(containerColor = Color.Red),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {

                    }
                    UiHandler.ViewTabAndPager(
                        modifier = Modifier.fillMaxWidth(),
                        modifierPager = Modifier.fillMaxSize(),
                        modifierTabRow = Modifier
                            .fillMaxWidth(),
                        tabs = tabs,
                        initializePage = 1
                    ) { pageNumber ->
                        when (pageNumber) {
                            0 -> ViewSingleCourseComment(
                                viewModel = viewModel,
                                comments = courseData.value!!.comment,
                                courseId = courseId
                            )

                            else -> ViewSingleCourseData(
                                viewModel = viewModel,
                                course = courseData.value!!.course,
                                seasons = courseData.value!!.seasons,
                                courseId = courseId,
                                navController = navController
                            )
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    viewModel.addToFavorites.observe(lifecycle) {
                        if (it) {
                            liked.value = true
                            Toast.makeText(context, "به علاقمندی ها اضافه شد", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                context,
                                "خطا در اضافه شدن به علاقمندی",
                                Toast.LENGTH_SHORT
                            ).show()
                            liked.value = false
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun ViewSingleCourseData(
    courseId: Int,
    viewModel: SingleCourseViewModel,
    course: ApiResponseGetCourseCourseData,
    seasons: ArrayList<ApiResponseGetCourseSeasons>,
    navController: NavController,
) {
    val lifecycle = LocalLifecycleOwner.current
    val addCourseToMyCartError = remember {
        mutableStateOf(false)
    }
    val addedToMyCart = remember {
        mutableStateOf(course.courseInMyCart)
    }
    val addToMyCartLoading = remember {
        mutableStateOf(false)
    }
    val hasBeenPurchased = remember {
        mutableStateOf(course.hasBeenPurchased)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AmozeshgamTheme.colors["background"]!!)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(AmozeshgamTheme.colors["background"]!!)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = course.title,
                fontSize = 30.sp,
                textAlign = TextAlign.Right,
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_semibold))
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right
            ) {
                UiHandler.ClipItem(image = R.drawable.ic_person, text = course.teacher)
                UiHandler.ClipItem(image = R.drawable.ic_clock, text = course.time)
                UiHandler.ClipItem(image = R.drawable.ic_star, text = course.rate)
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 10.dp),
                text = course.description,
                textAlign = TextAlign.Right,
                fontSize = 15.sp,
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp),
                text = "سرفصل ها",
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_black))
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2).dp)
            ) {
                items(seasons.size) { index ->
                    UiHandler.DropdownList(
                        modifier = Modifier.padding(5.dp), title = seasons[index].title
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = (LocalConfiguration.current.screenHeightDp / 2).dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            items(seasons[index].sub.size) { subIndex ->
                                Column(modifier = Modifier.padding(7.dp)) {
                                    UiHandler.AnythingRow(itemOne = {
                                        Text(
                                            text = seasons[index].sub[subIndex].title,
                                            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                                            fontSize = 17.sp,
                                            color = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }, itemTwo = {
                                        Icon(
                                            modifier = Modifier.size(30.dp),
                                            painter = painterResource(R.drawable.ic_video),
                                            contentDescription = null,
                                            tint = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    })
                                    UiHandler.AnythingRow(itemOne = {
                                        Text(
                                            text = seasons[index].sub[subIndex].time,
                                            color = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }, itemTwo = {
                                        Spacer(modifier = Modifier.size(30.dp))
                                    })
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(90.dp))
        }

        if (!hasBeenPurchased.value) {
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    .shadow(
                        5.dp,
                        ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                        spotColor = AmozeshgamTheme.colors["shadowColor"]!!
                    ),
                shape = RoundedCornerShape(0),
                colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 10.dp, vertical = 2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (course.finalPrice == 0) "رایگان!" else DecimalFormat(",000").format(
                                course.finalPrice
                            ),
                            color = AmozeshgamTheme.colors["textColor"]!!,
                            fontSize = 20.sp,
                            fontFamily = AmozeshgamTheme.fonts["regular"],
                        )
                        if (course.finalPrice != course.price.toInt()) {
                            Text(
                                text = DecimalFormat(",000").format(course.price.toInt()),
                                color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 12.sp,
                                fontFamily = AmozeshgamTheme.fonts["regular"]
                            )
                        }
                        Text(
                            text = "تومان",
                            color = AmozeshgamTheme.colors["secondaryTextColor"]!!,
                            fontSize = 14.sp,
                            fontFamily = AmozeshgamTheme.fonts["regular"]
                        )
                    }
                    if (!addedToMyCart.value) {
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                                .height(50.dp),
                            onClick = {
                                if (!addToMyCartLoading.value) {
                                    addToMyCartLoading.value = true
                                    viewModel.addCourseToMyCart(courseId = courseId)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmozeshgamTheme.colors["primary"]!!
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            if (addToMyCartLoading.value) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(30.dp),
                                    color = Color.White
                                )
                            } else {
                                if (course.finalPrice != 0) {
                                    Text(text = "اضافه کردن به سبد خرید")
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_cart),
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                } else {
                                    Text(text = "شروع یادگیری")
                                }
                            }
                        }
                    } else {
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                                .height(50.dp),
                            onClick = {
                                navController.navigate(NavigationScreenHandler.MyCartScreen.route)
                            },
                            border = BorderStroke(1.dp, AmozeshgamTheme.colors["primary"]!!),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AmozeshgamTheme.colors["background"]!!
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_left),
                                contentDescription = null,
                                tint = AmozeshgamTheme.colors["primary"]!!
                            )
                            Text(
                                text = "ادامه و پرداخت",
                                color = AmozeshgamTheme.colors["primary"]!!,
                            )
                        }
                    }
                }
            }
        }
        if (addCourseToMyCartError.value) {

        }
        LaunchedEffect(Unit) {
            viewModel.addCourseToMyCart.observe(lifecycle) {
                addToMyCartLoading.value = false
                when (it) {
                    RemoteStateHandler.OK -> {
                        addedToMyCart.value = true
                        if (course.finalPrice == 0) {
                            hasBeenPurchased.value = true
                        }
                    }

                    RemoteStateHandler.ERROR -> {
                        addCourseToMyCartError.value = true
                    }

                    else -> Unit
                }
            }
        }
    }
}

@Composable
fun ViewSingleCourseComment(
    viewModel: SingleCourseViewModel,
    comments: ArrayList<ApiResponseGetCourseComment>?,
    courseId: Int,
) {
    val showCommentDialog = remember {
        mutableStateOf(false)
    }
    val buttonDialogLoading = remember {
        mutableStateOf(false)
    }
    val commentBody = remember {
        mutableStateOf("")
    }
    val ratingComment = remember {
        mutableFloatStateOf(2f)
    }
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AmozeshgamTheme.colors["background"]!!)
    ) {
        if (comments.isNullOrEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "دیدگاهی وجود ندارد",
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
        } else {
            LazyColumn {
                items(comments.size) { index ->
                    CommentItem(
                        avatarLink = comments[index].avatar,
                        username = comments[index].name,
                        date = comments[index].date,
                        body = comments[index].comment
                    )
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(15.dp)
                .size(60.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                showCommentDialog.value = true
            },
            containerColor = AmozeshgamTheme.colors["primary"]!!
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_comment),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
    if (showCommentDialog.value) {
        UiHandler.CustomDialog(
            onDismiss = {
                showCommentDialog.value = false
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(
                        modifier = Modifier
                            .padding(5.dp)
                            .heightIn(max = 200.dp),
                        text = "ثبت دیدگاه",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.yekan_bakh_black)),
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                    IconButton(onClick = { showCommentDialog.value = false }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "به این دوره چه امتیازی می دهید",
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                RatingBar(
                    modifier = Modifier.padding(5.dp),
                    rating = ratingComment.floatValue,
                    tintEmpty = Color.White,
                    tintFilled = Color.Red,
                    imageVectorEmpty = ImageVector.vectorResource(id = R.drawable.ic_like),
                    imageVectorFilled = ImageVector.vectorResource(id = R.drawable.ic_like_fill),
                    rateChangeStrategy = RateChangeStrategy.AnimatedChange(),
                    ratingInterval = RatingInterval.Half,
                    gestureStrategy = GestureStrategy.DragAndPress
                ) { rate: Float ->
                    ratingComment.floatValue = rate
                }
                UiHandler.OutLineTextField(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .heightIn(min = 200.dp),
                    value = commentBody.value,
                    onValueChange = {
                        commentBody.value = it
                    },
                    keyboardOptions = KeyboardOptions.Default,
                    label = {
                        Text(
                            text = "متن دیدگاه",
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                    }
                )
                Button(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {
                        if (!buttonDialogLoading.value) {
                            viewModel.addCommentToCourse(
                                comment = commentBody.value,
                                courseId = courseId
                            )
                            buttonDialogLoading.value = true
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AmozeshgamTheme.colors["primary"]!!
                    )
                ) {
                    Text(text = "ثبت دیدگاه")
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.addCommentToCourse.observe(lifecycle) {
            buttonDialogLoading.value = false
            viewModel.addCommentToCourse.observe(lifecycle) {
                when (it) {
                    RemoteStateHandler.OK -> {
                        Toast.makeText(context, "دیدگاه با موفقیت ثبت شد", Toast.LENGTH_SHORT)
                            .show()
                    }

                    RemoteStateHandler.ERROR -> {
                        Toast.makeText(
                            context,
                            "خطا در ثبت دیدگاه", Toast.LENGTH_SHORT
                        )

                            .show()
                    }

                    else -> Unit
                }
                viewModel.resetCommentState()
            }
        }
    }
}