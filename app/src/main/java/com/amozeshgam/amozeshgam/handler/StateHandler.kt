package com.amozeshgam.amozeshgam.handler

enum class RemoteStateHandler{
    WAITING,
    ERROR,
    OK,
    LOADING,
    BADRESPONSE
}
enum class ValidatingStateHandler{
    VALID,
    INVALID,
    ERROR,
    WAITING
}
enum class BatteryStateHandler{
    HIGHLEVEL,
    MIDLEVEL,
    LOWLEVEL
}
enum class NotificationPlayerState{
    PLAY,
    PAUSE
}
enum class DownloadServiceState{
    START,
    PAUSE,
    CANCEL
}

enum class AppWidgetState{
    STARTAPP,
    OPENCOURSESCREEN,
    OPENROADMAPSCREEN
}
enum class ActivityName{
    LoginActivity,
    HomeActivity,
    TourActivity
}