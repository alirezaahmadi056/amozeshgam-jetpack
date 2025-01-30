package com.amozeshgam.amozeshgam.handler

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeepLinkHandler @Inject constructor() {
    fun createLinkWithRoute(route: String): String {
        return "https://amozeshgam.com/$route"
    }

    fun whichActivityToGo(link: String): String {
        val clearedLink = link.replace("https://amozeshgam.com/", "").split("/")
        return when (clearedLink[0]) {
            NavigationHandler.PodcastPlayerScreen.route -> NavigationHandler.PodcastPlayerScreen.route + "/" + (clearedLink.getOrNull(
                1
            ) ?: 0).toString()

            NavigationHandler.SingleCourseScreen.route -> NavigationHandler.SingleCourseScreen.route + "/" + (clearedLink.getOrNull(
                1
            ) ?: 0).toString()

            NavigationHandler.StoryScreen.route -> NavigationHandler.StoryScreen.route + "/" + (clearedLink.getOrNull(
                1
            ) ?: 0).toString()

            NavigationHandler.SingleNewsScreen.route -> NavigationHandler.SingleNewsScreen.route + "/" + (clearedLink.getOrNull(
                1
            ) ?: 0).toString()

            else -> NavigationHandler.HomeScreen.route
        }
    }
}