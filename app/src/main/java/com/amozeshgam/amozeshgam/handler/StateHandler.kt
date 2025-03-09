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



enum class NotificationPlayerState {
    PLAY_AND_PAUSE,
    DESTROY
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



enum class ScreenName{
    LOGIN,
    HOME,
    TOUR
}

enum class BiometricState {
    AUTHENTICATED,
    SUCCESS,
    ERROR,
    NO_BIOMETRIC_AVAILABLE
}