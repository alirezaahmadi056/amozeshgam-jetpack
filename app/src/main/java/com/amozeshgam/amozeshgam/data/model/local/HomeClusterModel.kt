package com.amozeshgam.amozeshgam.data.model.local

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NavDrawerMode
import com.amozeshgam.amozeshgam.handler.NavigationScreenHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import javax.inject.Inject

class HomeActivityModel @Inject constructor() {
    val storyTimer = 60
    val navItems = arrayOf(
        NavItem(
            NavigationScreenHandler.ProfileScreen.route,
            R.drawable.ic_profile_fill,
            R.drawable.ic_profile
        ),
        NavItem(
            NavigationScreenHandler.NewsScreen.route,
            R.drawable.ic_news_fill,
            R.drawable.ic_news
        ),
        NavItem(
            NavigationScreenHandler.MyCartScreen.route,
            R.drawable.ic_cart_fill,
            R.drawable.ic_cart
        ),
        NavItem(
            NavigationScreenHandler.AiChatScreen.route,
            R.drawable.ic_message_fill,
            R.drawable.ic_message
        ),
        NavItem(
            NavigationScreenHandler.HomeScreen.route,
            R.drawable.ic_home_fill,
            R.drawable.ic_home
        )
    )
    val drawerNavItem = arrayOf(
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "مسیر های یادگیری",
            icon = R.drawable.ic_simple_road_map
        ),

        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "دوره های من",
            icon = R.drawable.ic_book
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "پشتیبانی",
            icon = R.drawable.ic_support
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "درباره ما",
            icon = R.drawable.ic_about
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "تماس با ما",
            icon = R.drawable.ic_contacts
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "اشتراک گذاری برنامه",
            icon = R.drawable.ic_share,
            mode = NavDrawerMode.LINK
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "امتیاز به برنامه",
            icon = R.drawable.ic_star,
            mode = NavDrawerMode.LINK
        ),
        NavDrawerItem(
            route = NavigationScreenHandler.MyRoadMapScreen.route,
            title = "حالت تیره",
            icon = R.drawable.ic_simple_road_map,
            state = mutableStateOf(GlobalUiModel.uiTheme.intValue == GlobalUiModel.DARK_CODE),
            mode = NavDrawerMode.SWITCH
        ),

        )
    val profileItem = arrayOf(
        ProfileItem(
            R.drawable.ic_roadmap,
            "مسیر های یادگیری",
            NavigationScreenHandler.MyRoadMapScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_package,
            "دوره های من",
            NavigationScreenHandler.MyPackageScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_active_device,
            "دستگاه های فعال",
            NavigationScreenHandler.MyActiveDeviceScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_cart,
            "سفارشات من",
            NavigationScreenHandler.MyOrdersScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_like,
            "علاقه مندی ها",
            NavigationScreenHandler.MyFavorites.route
        ),
        ProfileItem(
            R.drawable.ic_counseling,
            "مشاوره ی تخصصی",
            NavigationScreenHandler.MessageScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_support,
            "پشتیبانی",
            NavigationScreenHandler.SupportScreen.route
        ),
        ProfileItem(
            R.drawable.ic_ui_setting,
            "تنظیمات",
            "dialog"
        )
    )

    val fieldTabItem = arrayListOf(
        "پیشنیاز",
        "زیرشاخه",
    )


    val courseFilterItem = arrayOf(
        CourseFilter(
            selected = mutableStateOf(true),
            title = "کل دوره ها",
            selectedBackgroundColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBackgroundColor = { AmozeshgamTheme.colors["background"]!! },
            selectedBorderColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBorderColor = { AmozeshgamTheme.colors["borderColor"]!! },
            selectedIcon = R.drawable.ic_all_fill,
            unSelectedIcon = R.drawable.ic_all,
            unSelectedTextColor = { AmozeshgamTheme.colors["borderColor"]!! }
        ),
        CourseFilter(
            selected = mutableStateOf(false),
            title = "پرطرفدار ها",
            selectedBackgroundColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBackgroundColor = { AmozeshgamTheme.colors["background"]!! },
            selectedBorderColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBorderColor = { AmozeshgamTheme.colors["borderColor"]!! },
            selectedIcon = R.drawable.ic_popular_fill,
            unSelectedIcon = R.drawable.ic_popular,
            unSelectedTextColor = { AmozeshgamTheme.colors["borderColor"]!! }
        ),
        CourseFilter(
            selected = mutableStateOf(false),
            title = "جدیدترین ها",
            selectedBackgroundColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBackgroundColor = { AmozeshgamTheme.colors["background"]!! },
            selectedBorderColor = { AmozeshgamTheme.colors["primary"]!! },
            unSelectedBorderColor = { AmozeshgamTheme.colors["borderColor"]!! },
            selectedIcon = R.drawable.ic_new_fill,
            unSelectedIcon = R.drawable.ic_new,
            unSelectedTextColor = { AmozeshgamTheme.colors["borderColor"]!! }
        )
    )

}

data class NavItem(
    val route: String,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
) {
    fun isSelected(currentRoute: String): Boolean {
        return this.route == currentRoute || currentRoute.startsWith(this.route)
    }
}

data class NavDrawerItem(
    val route: String,
    val title: String,
    val icon: Int,
    val state: MutableState<Boolean>? = null,
    val mode: NavDrawerMode = NavDrawerMode.NORMAL
)

data class ProfileItem(
    val icon: Int,
    val label: String,
    val route: String,
)


data class CourseFilter(
    val selected: MutableState<Boolean>,
    val title: String,
    val selectedBackgroundColor: @Composable () -> Color,
    val unSelectedBackgroundColor: @Composable () -> Color,
    val selectedBorderColor: @Composable () -> Color,
    val unSelectedBorderColor: @Composable () -> Color,
    val selectedIcon: Int,
    val unSelectedIcon: Int,
    val unSelectedTextColor: @Composable () -> Color,
)


