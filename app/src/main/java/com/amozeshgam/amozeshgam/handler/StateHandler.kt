package com.amozeshgam.amozeshgam.handler

enum class RemoteStateHandler {
    WAITING,
    ERROR,
    OK,
    LOADING,
    BAD_RESPONSE
}

enum class ValidatingStateHandler {
    VALID,
    INVALID,
    ERROR,
    WAITING
}

enum class BatteryStateHandler {
    HIGH_LEVEL,
    MID_LEVEL,
    LOW_LEVEL
}

enum class NotificationPlayerState {
    PLAY,
    PAUSE
}
enum class NavDrawerMode {
    NORMAL,
    SWITCH,
    LINK,
}

enum class DownloadServiceState {
    START,
    PAUSE,
    CANCEL
}

enum class AppWidgetState {
    STARTAPP,
    OPEN_COURSE_SCREEN,
    OPEN_ROADMAP_SCREEN
}

enum class ScreenName{
    LOGIN,
    HOME,
    SPLASH,
    TOUR
}

enum class BiometricState {
    AUTHENTICATED,
    SUCCESS,
    ERROR,
    NO_BIOMETRIC_AVAILABLE
}