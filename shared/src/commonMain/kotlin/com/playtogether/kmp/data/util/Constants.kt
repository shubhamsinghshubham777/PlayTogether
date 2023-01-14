package com.playtogether.kmp.data.util

object StringConstants {
    const val GenericErrorMessage = "Something went wrong! Please try again later."
    const val UrlNotFound = "404: The requested screen was not found! Please check the URL again."
    object Auth {
        const val EmailLabel = "E-mail"
        const val PasswordLabel = "Password"
        const val RepeatPasswordLabel = "Repeat Password"
        const val InvalidPassword = "A valid password contains at least 8 characters with 1 " +
                "uppercase, 1 lowercase, 1 numeric and 1 special character"
        const val InvalidEmail = "Email is not valid"
        const val InvalidRepeatPassword = "Passwords do not match"
        const val LoginAuthTypeMessage = "Not a member?\nSign up instead"
        const val RegisterAuthTypeMessage = "Already a member?\nSign in instead"
    }
}

object AnimationConstants {
    const val SnackbarShortDuration = 4000
    const val SnackbarLongDuration = 10000
}
