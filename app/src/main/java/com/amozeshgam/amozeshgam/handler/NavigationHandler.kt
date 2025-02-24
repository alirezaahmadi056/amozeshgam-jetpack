package com.amozeshgam.amozeshgam.handler

sealed class NavigationScreenHandler(val route: String) {
    data object HomeScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/homeScreen")

    data object NewsScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/newsScreen")

    data object ProfileScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/profileScreen")

    data object MessageScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/profileScreen/messageScreen")

    data object AllPodcastScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/homeScreen/allPodcastScreen")

    data object AllCoursesScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/allPackageScreen")

    data object MyPackageScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myPackageScreen")

    data object MyRoadMapScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myRoadMapScreen")

    data object MyActiveDeviceScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myActiveDeviceScreen")

    data object MyCartScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/myCartScreen")

    data object MyFavorites :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myFavoritesScreen")

    data object SupportScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/supportScreen")

    data object AiChatScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/aiChatScreen")

    data object StoryScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/storyScreen")

    data object PodcastPlayerScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/podcastPlayerScreen")

    data object SingleCourseScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/singlePackageScreen")

    data object FAQScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/FAQScreen")

    data object InformationScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/informationScreen")

    data object NotificationScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/notificationScreen")

    data object TicketScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/ticketScreen")

    data object SingleNewsScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/newsScreen/singleNewsScreen")

    data object MyOrdersScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myOrdersScreen")

    data object MyTransactionsScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/myTransactionsScreen")

    data object ProfileEditorScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/profileScreen/profileEditorScreen")

    data object FieldScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/fieldScreen")

    data object GuidanceScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/guidanceScreen")

    data object PaymentScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/myCartScreen/payment")

    data object SingleSubFieldScreen :
        NavigationScreenHandler("${NavigationClusterHandler.Home.route}/homeScreen/subField")

    data object LoginScreenOne :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Login.route}/loginOneScreen")

    data object LoginScreenTwo :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Login.route}/loginTwoScreen")

    data object LoginScreenThree :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Login.route}/loginThreeScreen")

    data object SplashScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Splash.route}/splashScreen")

    data object AIQuestionScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/homeScreen/AIQuestionScreen")

    data object GuidancePurchaseScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/GuidancePurchaseScreen")

    data object TourScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Splash.route}/tourScreen")

    data object GuidanceSingleScreen :
        NavigationScreenHandler(route = "${NavigationClusterHandler.Home.route}/GuidanceSingleScreen")
}

sealed class NavigationClusterHandler(val route: String) {
    data object Home : NavigationClusterHandler(route = "home")
    data object Login : NavigationClusterHandler(route = "login")
    data object Splash : NavigationClusterHandler(route = "splash")
}