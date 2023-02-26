package com.playtogether.kmp.data.util

object Constants {
    const val GenericErrorMessage = "Something went wrong! Please try again later."
    const val UrlNotFound = "404: The requested screen was not found! Please check the URL again."
    const val AppGreeting = "Welcome to ${App.Name}"
    const val AppGreetingSupportingText = "Please authenticate to access the app"

    object App {
        const val Name = "PlayTogether"
        const val ShortDescription = "Watch and enjoy movies, TV shows, or any kind of visual media with " +
                "your friends and family"
    }

    object Auth {
        const val Title = "Authentication"
        const val EmailLabel = "E-mail"
        const val PasswordLabel = "Password"
        const val RepeatPasswordLabel = "Repeat Password"
        const val InvalidPassword = "A valid password contains at least 8 characters with 1 " +
                "uppercase, 1 lowercase, 1 numeric and 1 special character"
        const val InvalidEmail = "Email is not valid"
        const val InvalidRepeatPassword = "Passwords do not match"
        const val LoginAuthTypeMessage = "Not a member?\nSign up instead"
        const val RegisterAuthTypeMessage = "Already a member?\nSign in instead"
        const val LoginButtonLabel = "Login"
        const val RegisterButtonLabel = "Register"
        const val AccountDeletionWarning = "Are you sure you want to delete your account? " +
                "Please note that this process is permanent and irreversible!"
        const val AccountDeletionSuccess = "User was deleted successfully!"
    }

    object Animation {
        const val SnackbarShortDuration = 4000
        const val SnackbarLongDuration = 10000
    }

    object NavRoutes {
        const val Root = "/"
        const val Auth = "/auth"

        object Params {
            const val continueRoute = "continue"
        }
    }

    object Server {
        object Routes {
            const val base = "/"
            const val login = "/login"
            const val register = "/register"
            const val updateUserProfile = "/updateUserProfile"
            const val deleteUserProfile = "/deleteUserProfile"
            const val updateAccessToken = "/updateAccessToken"
        }

        object SecretKeys {
            const val JWTSecret = "JWT_SECRET"
            const val DBUser = "DB_USER"
            const val DBPassword = "DB_PASSWORD"
            const val DBHost = "DB_HOST"
            const val DBPort = "DB_PORT"
            const val DBName = "DB_NAME"
            const val AWSBucketName = "AWS_BUCKET_NAME"
            const val AWSRegion = "AWS_REGION"
            // These values are coming from application.conf file
            const val JWTIssuer = "jwt.issuer"
            const val JWTAudience = "jwt.audience"
            const val JWTRealm = "jwt.realm"
        }

        const val DBDriver = "org.postgresql.Driver"

        object Exceptions {
            const val UserNotFound = "No user found with the given credentials!"
            const val UserAlreadyExists = "Cannot register user since the given" +
                    " credentials already exist in the system."

            fun invalidParameter(paramName: String) = "Either the provided parameter '$paramName' is" +
                    " of invalid type or not provided at all."

            fun generic(reason: String?) = "Unexpected error occurred! Reason: $reason"
            const val InvalidEmail = "The e-mail provided is not valid!"
            const val InvalidPassword = "A valid password contains at least 8 characters with 1 " +
                    "uppercase, 1 lowercase, 1 numeric and 1 special character"
            const val InvalidCredentials = "Credentials entered are invalid!"
            const val InvalidHashPassword = "Hashed password is not provided for the user!"
            const val InvalidSalt = "No encryption salt provided for the user!"
            const val InvalidAuthToken =
                "No email found for the given auth credentials! Please sign in again!"
            fun illegalEnvironmentVariable(variableName: String) = "The expected variable " +
                    "$variableName is not found in system environment! Please check your setup."
            const val BlankUserName = "The name cannot be set to blank. Please write a valid name!"
        }

        object Params {
            const val UserEmail = "email"
            const val UserPassword = "password"
            const val UserName = "name"
        }

        object ResponseMessages {
            const val UserUpdateSuccess = "User was updated successfully!"
            const val UserUpdateFailure = "User was not updated!"
        }

        const val JWTClaimEmail = "userEmail"
    }

    object NetworkClientEndpoints {
        private const val BaseURL = "http://192.168.1.46:8080"
        const val Login = BaseURL + Server.Routes.login
        const val Register = BaseURL + Server.Routes.register
    }

    object Desktop {
        const val WindowTitle = "PlayTogether: Enjoy Media with Friends"
    }

    object SharedPrefKeys {
        const val AuthToken = "authToken"
        const val IsDarkThemeOn = "isDarkThemeOn"
    }
}

object RegexPatterns {
    val Email = Regex(
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    )

    val Password = Regex(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$"
    )
}
