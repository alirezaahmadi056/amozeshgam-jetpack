package com.amozeshgam.amozeshgam.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.screens.home.ViewHome
import com.amozeshgam.amozeshgam.view.screens.home.ViewInformation
import com.amozeshgam.amozeshgam.view.screens.home.ViewNotification
import com.amozeshgam.amozeshgam.view.screens.home.ViewStory
import com.amozeshgam.amozeshgam.view.screens.home.chat.ViewAiChat
import com.amozeshgam.amozeshgam.view.screens.home.chat.ViewChat
import com.amozeshgam.amozeshgam.view.screens.home.courses.ViewCourses
import com.amozeshgam.amozeshgam.view.screens.home.courses.ViewSingleCourse
import com.amozeshgam.amozeshgam.view.screens.home.news.ViewNews
import com.amozeshgam.amozeshgam.view.screens.home.news.ViewSingleNews
import com.amozeshgam.amozeshgam.view.screens.home.payment.ViewCart
import com.amozeshgam.amozeshgam.view.screens.home.payment.ViewPayment
import com.amozeshgam.amozeshgam.view.screens.home.podcasts.ViewPodcastPlayer
import com.amozeshgam.amozeshgam.view.screens.home.podcasts.ViewPodcasts
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewActiveDevice
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewEditProfile
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewFAQ
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewMyFavorites
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewMyOrders
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewMyPackage
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewMyRoadMap
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewMyTransaction
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewProfile
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewSupport
import com.amozeshgam.amozeshgam.view.screens.home.profile.ViewTicket
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.field.ViewField
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.field.ViewSingleSubField
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.job.ViewJobs
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.job.ViewJobsPurchase
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.question.ViewAiQuestion
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startService()
        setContent {
            ViewMainContent(deepLink = intent.getStringExtra("deep-link"))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun ViewMainContent(viewModel: HomeViewModel = hiltViewModel(), deepLink: String?) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val navItems = viewModel.getNavItems()
    val showMenuItem = remember {
        mutableStateOf(false)
    }
    val showDefaultNavigation = remember {
        mutableStateOf(true)
    }
    val showDefaultTopAppBar = remember {
        mutableStateOf(true)
    }
    val snackHostState = remember {
        SnackbarHostState()
    }
    val coroutine = rememberCoroutineScope()
    AmozeshgamTheme(
        darkTheme = UiHandler.themeState()
    ) {
        Scaffold(topBar = {
            if (showDefaultTopAppBar.value) {
                TopAppBar(
                    modifier = Modifier.shadow(
                        50.dp,
                        spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                        ambientColor = AmozeshgamTheme.colors["shadowColor"]!!
                    ),
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Image(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxHeight()
                                    .padding(12.dp),
                                painter = painterResource(id = AmozeshgamTheme.assets["amozeshgamBanner"]!!),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight
                            )

                        }
                    },
                    navigationIcon = {
                        IconButton(modifier = Modifier.size(67.dp), onClick = {
                            navController.navigate(NavigationHandler.NotificationScreen.route)
                        }) {
                            if (GlobalUiModel.numberOfMessage.intValue != 0) {
                                BadgedBox(badge = {
                                    Badge(
                                        modifier = Modifier.padding(end = 10.dp),
                                        containerColor = AmozeshgamTheme.colors["badgeBoxColor"]!!,
                                        contentColor = AmozeshgamTheme.colors["primary"]!!
                                    ) {
                                        Text(
                                            text = GlobalUiModel.numberOfMessage.intValue.toString(),
                                            color = Color.White
                                        )
                                    }
                                }) {
                                    Icon(
                                        modifier = Modifier.padding(5.dp),
                                        painter = painterResource(id = R.drawable.ic_notification),
                                        contentDescription = null,
                                        tint = AmozeshgamTheme.colors["textColor"]!!
                                    )
                                }
                            } else {
                                Icon(
                                    modifier = Modifier.padding(5.dp),
                                    painter = painterResource(id = R.drawable.ic_notification),
                                    contentDescription = null,
                                    tint = AmozeshgamTheme.colors["textColor"]!!
                                )
                            }
                        }
                    },
                    actions = {
                        if (currentRoute == NavigationHandler.HomeScreen.route) {
                            IconButton(onClick = {
                                showMenuItem.value = true
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_more),
                                    contentDescription = null,
                                    tint = AmozeshgamTheme.colors["textColor"]!!
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                viewModel.clearAllExoplayer()
                                navController.popBackStack()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_arrow_right),
                                    contentDescription = null,
                                    tint = AmozeshgamTheme.colors["textColor"]!!
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                )
            }
        }, bottomBar = {
            if (showDefaultNavigation.value) {

                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .graphicsLayer { clip = false }
                        .drawBehind {
                            val shadowHeight = 2.dp.toPx()
                            val gradientBrush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.1f),
                                    Color.Transparent
                                ),
                                startY = 0f,
                                endY = shadowHeight
                            )
                            drawRect(
                                brush = gradientBrush,
                                topLeft = Offset(0f, -shadowHeight),
                                size = Size(size.width, shadowHeight)
                            )
                        }.shadow(5.dp),

                    containerColor = AmozeshgamTheme.colors["background"]!!,
                    tonalElevation = 10.dp,
                ) {
                    repeat(navItems.size) { index ->
                        NavigationBarItem(selected = false, onClick = {
                            navController.navigate(navItems[index].route)
                        }, icon = {
                            if (index == 2 && GlobalUiModel.numberOfCart.intValue != 0) {
                                BadgedBox(badge = {
                                    Badge(
                                        containerColor = AmozeshgamTheme.colors["badgeBoxColor"]!!,
                                        contentColor = AmozeshgamTheme.colors["primary"]!!
                                    ) {
                                        Text(
                                            text = GlobalUiModel.numberOfCart.intValue.toString(),
                                            color = Color.White
                                        )
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (navItems[index].isSelected(
                                                    currentRoute.toString()
                                                )
                                            ) navItems[index].selectedIcon else navItems[index].unSelectedIcon
                                        ),
                                        contentDescription = null,
                                        tint = if (navItems[index].isSelected(currentRoute.toString())) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!
                                    )
                                }
                            } else {
                                Icon(
                                    painter = painterResource(
                                        id = if (navItems[index].isSelected(
                                                currentRoute.toString()
                                            )
                                        ) navItems[index].selectedIcon else navItems[index].unSelectedIcon
                                    ),
                                    contentDescription = null,
                                    tint = if (navItems[index].isSelected(currentRoute.toString())) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!
                                )
                            }
                        })
                    }
                }
            }
        }, containerColor = AmozeshgamTheme.colors["background"]!!) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = deepLink ?: NavigationHandler.HomeScreen.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popExitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None }
            ) {


                composable(route = NavigationHandler.HomeScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewHome(navController = navController)
                }
                composable(route = NavigationHandler.AiChatScreen.route) {
                    showDefaultNavigation.value = true
                    showDefaultTopAppBar.value = true
                    ViewAiChat(navController = navController)
                }
                composable(route = NavigationHandler.ProfileScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewProfile(navController = navController)
                }
                composable(route = NavigationHandler.MessageScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewChat(navController = navController)
                }
                composable(route = NavigationHandler.NewsScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewNews(navController = navController)
                }
                composable(route = NavigationHandler.AllPodcastScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewPodcasts(navController = navController)
                }
                composable(route = NavigationHandler.AIQuestionScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewAiQuestion(navController = navController)
                }
                composable(route = NavigationHandler.AllCoursesScreen.route) {
                    ViewCourses(navController = navController)
                }
                composable(route = NavigationHandler.MyPackageScreen.route) {
                    showDefaultTopAppBar.value = false
                    ViewMyPackage(navController = navController)
                }
                composable(route = NavigationHandler.MyFavorites.route) {
                    showDefaultTopAppBar.value = false
                    ViewMyFavorites(navController = navController)
                }
                composable(route = NavigationHandler.SupportScreen.route) {
                    showDefaultTopAppBar.value = false
                    ViewSupport(navController = navController)
                }
                composable(route = NavigationHandler.MyCartScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewCart(navController = navController)
                }
                composable(route = NavigationHandler.PaymentScreen.route) {
                    ViewPayment(navController = navController)
                }
                composable(route = NavigationHandler.MyActiveDeviceScreen.route) {
                    showDefaultTopAppBar.value = false
                    ViewActiveDevice(navController = navController)
                }
                composable(
                    route = "${NavigationHandler.StoryScreen.route}/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = false
                    ViewStory(navController = navController)
                }
                composable(
                    route = "${NavigationHandler.PodcastPlayerScreen.route}/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = false
                    ViewPodcastPlayer(navController = navController)
                }
                composable(route = NavigationHandler.TicketScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewTicket(navController = navController)
                }
                composable(
                    route = "${NavigationHandler.SingleCourseScreen.route}/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = false
                    ViewSingleCourse(navController = navController)
                }
                composable(route = NavigationHandler.FAQScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewFAQ(navController = navController)
                }
                composable(route = NavigationHandler.InformationScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = true
                    ViewInformation(navController = navController)
                }
                composable(
                    route = "${NavigationHandler.SingleNewsScreen.route}/{id}",
                    arguments = listOf(navArgument("id") {
                        type = NavType.IntType
                    })
                ) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = false
                    ViewSingleNews(navController = navController)
                }
                composable(route = NavigationHandler.NotificationScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewNotification(navController = navController)
                }
                composable(route = NavigationHandler.MyOrdersScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewMyOrders(navController = navController)
                }
                composable(route = NavigationHandler.MyTransactionsScreen.route) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewMyTransaction(navController = navController)
                }
                composable(route = "${NavigationHandler.ProfileEditorScreen.route}/{username}/{phone}/{birthday}/{email}/{avatar}",
                    arguments = listOf(navArgument("username") {
                        type = NavType.StringType
                    }, navArgument("phone") {
                        type = NavType.StringType
                    }, navArgument(
                        "birthday"
                    ) {
                        type = NavType.StringType
                    }, navArgument("avatar") {
                        type = NavType.StringType
                    })
                ) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewEditProfile(navController = navController)
                }
                composable(route = "${NavigationHandler.FieldScreen.route}/{id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.IntType
                        }
                    )) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = false
                    ViewField(navController = navController)
                }
                composable(route = NavigationHandler.JobsPurchaseScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = false
                    ViewJobsPurchase(navController = navController)
                }
                composable(route = NavigationHandler.JobsScreen.route) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = false
                    ViewJobs(navController = navController)
                }
                composable(route = "${NavigationHandler.SingleSubFieldScreen.route}/{id}",
                    arguments = listOf(
                        navArgument(name = "id") {
                            type = NavType.IntType
                        }
                    )) {
                    showDefaultTopAppBar.value = true
                    showDefaultNavigation.value = false
                    ViewSingleSubField(navController = navController)
                }
                composable(
                    route = NavigationHandler.MyRoadMapScreen.route
                ) {
                    showDefaultTopAppBar.value = false
                    showDefaultNavigation.value = true
                    ViewMyRoadMap(navController = navController)
                }
            }
            if (showMenuItem.value) {
                Popup(
                    onDismissRequest = {
                        showMenuItem.value = false
                    },
                    alignment = Alignment.TopEnd,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(padding)
                            .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                            .background(color = AmozeshgamTheme.colors["background"]!!)
                            .width(200.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        showMenuItem.value = false
                                        navController.navigate(NavigationHandler.TicketScreen.route)
                                    },
                                text = "پشتیبانی",
                                textAlign = TextAlign.Right,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        showMenuItem.value = false
                                    },

                                text = "تماس با ما",
                                textAlign = TextAlign.Right,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        showMenuItem.value = false
                                        viewModel.logOut()
                                        navController.navigate(NavigationHandler.LoginOneScreen.route) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                text = "خروج از حساب",
                                textAlign = TextAlign.Right,
                                color = AmozeshgamTheme.colors["textColor"]!!
                            )
                        }
                    }
                }
            }
            if (GlobalUiModel.requestForEnabledDarkMode.value) {
                coroutine.launch {
                    val snackResult = snackHostState.showSnackbar(
                        message = "حالت شب رو فعال کن تا شارژت دیرتر خالی بشه☺",
                        actionLabel = "فعال کردن",
                        duration = SnackbarDuration.Long
                    )
                    when (snackResult) {
                        SnackbarResult.ActionPerformed -> {
                            UiHandler.changeTheme(
                                themeCode = GlobalUiModel.DARK_CODE, dataBaseInputOutput = null
                            )
                        }

                        else -> Unit
                    }
                }
            }
            if (GlobalUiModel.errorExceptionDialog.value) {
                Log.i("jjj", "ViewMainContent: ${GlobalUiModel.errorExceptionMessage.value}")
                UiHandler.ErrorDialog(
                    imageId = R.drawable.ic_error,
                    text = ".برنامه با خطا مواجه شده است"
                ) {
                    Text(
                        modifier = Modifier.clickable {
                            viewModel.reportBug(GlobalUiModel.errorExceptionMessage.value)
                            GlobalUiModel.errorExceptionDialog.value = false
                        },
                        text = "اطلاع رسانی",
                        color = AmozeshgamTheme.colors["errorColor"]!!,
                        fontFamily = FontFamily(
                            Font(R.font.yekan_bakh_regular)
                        )
                    )
                    Text(
                        modifier = Modifier.clickable {
                            GlobalUiModel.errorExceptionDialog.value = false
                        },
                        text = "بستن",
                        color = AmozeshgamTheme.colors["primary"]!!,
                        fontFamily = AmozeshgamTheme.fonts["regular"]
                    )
                }
            }
            if (GlobalUiModel.showNotificationDialog.value) {
                UiHandler.AlertDialog(imageId = R.drawable.ic_message, title = "", description = "")
            }
        }
    }
}


