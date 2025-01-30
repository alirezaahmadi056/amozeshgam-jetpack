package com.amozeshgam.amozeshgam.data.model.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import javax.inject.Inject

class HomeActivityModel @Inject constructor() {
    val storyTimer = 60
    val navItems = arrayOf(
        NavItem(
            NavigationHandler.ProfileScreen.route,
            R.drawable.ic_profile_fill,
            R.drawable.ic_profile
        ),
        NavItem(
            NavigationHandler.NewsScreen.route,
            R.drawable.ic_news_fill,
            R.drawable.ic_news
        ),
        NavItem(
            NavigationHandler.MyCartScreen.route,
            R.drawable.ic_cart_fill,
            R.drawable.ic_cart
        ),
        NavItem(
            NavigationHandler.AiChatScreen.route,
            R.drawable.ic_message_fill,
            R.drawable.ic_message
        ),
        NavItem(
            NavigationHandler.HomeScreen.route,
            R.drawable.ic_home_fill,
            R.drawable.ic_home
        )
    )

    val profileItem = arrayOf(
        ProfileItem(
            R.drawable.ic_roadmap,
            "مسیر های یادگیری",
            NavigationHandler.MyRoadMapScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_package,
            "دوره های من",
            NavigationHandler.MyPackageScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_active_device,
            "دستگاه های فعال",
            NavigationHandler.MyActiveDeviceScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_cart,
            "سفارشات من",
            NavigationHandler.MyOrdersScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_like,
            "علاقه مندی ها",
            NavigationHandler.MyFavorites.route
        ),
        ProfileItem(
            R.drawable.ic_counseling,
            "مشاوره ی تخصصی",
            NavigationHandler.MessageScreen.route
        ),
        ProfileItem(
            R.drawable.ic_my_support,
            "پشتیبانی",
            NavigationHandler.SupportScreen.route
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


