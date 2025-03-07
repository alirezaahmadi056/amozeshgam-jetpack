package com.amozeshgam.amozeshgam.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.handler.NavigationClusterHandler
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
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
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.guidance.ViewGuidance
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.guidance.ViewGuidancePurchase
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.guidance.ViewSingleGuidance
import com.amozeshgam.amozeshgam.view.screens.home.roadMap.question.ViewAiQuestion
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginOne
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginThree
import com.amozeshgam.amozeshgam.view.screens.login.ViewLoginTwo
import com.amozeshgam.amozeshgam.view.screens.splash.ViewSplash
import com.amozeshgam.amozeshgam.view.screens.splash.ViewTour
import com.amozeshgam.amozeshgam.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var startDestination: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startBroadCast()
        if (intent.action == Intent.ACTION_SEND) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                viewModel.handleTextProvider(it)
            }

        }
        CoroutineScope(Dispatchers.IO).launch {
            if (intent.action == Intent.ACTION_VIEW && viewModel.userIsLoggedIn()) {
                startDestination = viewModel.handleDeepLink(intent.data.toString())
            }
        }
        setContent {
            ViewMainContent(startDestination = startDestination)
        }
    }
}

@Composable
fun ViewMainContent(startDestination: String? = null) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination ?: NavigationClusterHandler.Splash.route
    ) {
        navigation(
            route = NavigationClusterHandler.Splash.route,
            startDestination = NavigationScreenHandler.SplashScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() }
        ) {
            composable(NavigationScreenHandler.SplashScreen.route) {
                ViewSplash(navController = navController)
            }
            composable(NavigationScreenHandler.TourScreen.route) {
                ViewTour(navController = navController)
            }
        }
        navigation(
            route = NavigationClusterHandler.Login.route,
            startDestination = NavigationScreenHandler.LoginScreenOne.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            composable(NavigationScreenHandler.LoginScreenOne.route) {
                ViewLoginOne(navController = navController)
            }
            composable(
                NavigationScreenHandler.LoginScreenTwo.route + "/{phone}",
                arguments = listOf(navArgument("phone") { defaultValue = "" })
            ) {
                ViewLoginTwo(navController = navController)
            }
            composable(NavigationScreenHandler.LoginScreenThree.route) {
                ViewLoginThree(navController = navController)
            }
        }
        navigation(
            route = NavigationClusterHandler.Home.route,
            startDestination = NavigationScreenHandler.HomeScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },


            ) {
            composable(route = NavigationScreenHandler.HomeScreen.route, deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://app.amozeshgam.com/home"
                }
            )) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewHome(navController = it)
                }
            }
            composable(
                route = "${NavigationScreenHandler.GuidanceSingleScreen.route}/{id}",
                arguments = listOf(navArgument("id") { defaultValue = "" }),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "https://app.amozeshgam.com/guidance/{id}"
                    }
                )
            ) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewSingleGuidance(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.AiChatScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewAiChat(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.ProfileScreen.route) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewProfile(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MessageScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewChat(navController = navController)
                }
            }
            composable(route = NavigationScreenHandler.NewsScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                ) {
                    ViewNews(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.AllPodcastScreen.route) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewPodcasts(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.AIQuestionScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewAiQuestion(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.AllCoursesScreen.route) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewCourses(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyPackageScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewMyPackage(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyFavorites.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewMyFavorites(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.SupportScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewSupport(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyCartScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                ) {
                    ViewCart(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.PaymentScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewPayment(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyActiveDeviceScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewActiveDevice(navController = it)
                }
            }
            composable(
                route = "${NavigationScreenHandler.StoryScreen.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewStory(navController = it)
                }
            }
            composable(
                route = "${NavigationScreenHandler.PodcastPlayerScreen.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewPodcastPlayer(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.TicketScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewTicket(navController = it)
                }
            }
            composable(
                route = "${NavigationScreenHandler.SingleCourseScreen.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewSingleCourse(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.FAQScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewFAQ(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.InformationScreen.route) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewInformation(navController = it)
                }
            }
            composable(
                route = "${NavigationScreenHandler.SingleNewsScreen.route}/{id}",
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                })
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewSingleNews(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.NotificationScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewNotification(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyOrdersScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewMyOrders(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.MyTransactionsScreen.route) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewMyTransaction(navController = it)
                }
            }
            composable(route = "${NavigationScreenHandler.ProfileEditorScreen.route}/{username}/{phone}/{birthday}/{email}/{avatar}",
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
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false,
                    showDefaultTopAppBar = false
                ) {
                    ViewEditProfile(navController = it)
                }
            }
            composable(route = "${NavigationScreenHandler.FieldScreen.route}/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewField(navController = it)
                }
            }
            composable(
                route = NavigationScreenHandler.GuidancePurchaseScreen.route,
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewGuidancePurchase(navController = it)
                }
            }
            composable(route = NavigationScreenHandler.GuidanceScreen.route) {
                UiHandler.HomeScaffoldPattern(navController = navController) {
                    ViewGuidance(navController = it)
                }
            }
            composable(route = "${NavigationScreenHandler.SingleSubFieldScreen.route}/{id}",
                arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.IntType
                    }
                )) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultBottomBar = false
                ) {
                    ViewSingleSubField(navController = it)
                }
            }
            composable(
                route = NavigationScreenHandler.MyRoadMapScreen.route
            ) {
                UiHandler.HomeScaffoldPattern(
                    navController = navController,
                    showDefaultTopAppBar = false,
                    showDefaultBottomBar = false
                ) {
                    ViewMyRoadMap(navController = it)
                }
            }
        }
    }
}






