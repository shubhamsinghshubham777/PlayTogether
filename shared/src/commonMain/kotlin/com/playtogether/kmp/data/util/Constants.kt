package com.playtogether.kmp.data.util

object Constants {
    const val GenericErrorMessage = "Something went wrong! Please try again later."
    const val UrlNotFound = "404: The requested screen was not found! Please check the URL again."
    const val AppGreeting = "Welcome to PlayTogether"
    const val AppGreetingSupportingText = "Please authenticate to access the app"
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
        const val LoginButtonLabel = "Login"
        const val RegisterButtonLabel = "Register"
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
        }
        object SecretKeys {
            const val JWTSecret = "JWT_SECRET"
            const val JWTIssuer = "jwt.issuer"
            const val JWTAudience = "jwt.audience"
            const val JWTRealm = "jwt.realm"
            const val DBUser = "DB_USER"
            const val DBPassword = "DB_PASSWORD"
            const val DBUrl = "DB_URL"
        }

        const val DBDriver = "org.postgresql.Driver"

        object Exceptions {
            const val UserNotFound = "No user found with the given credentials!"
            const val UserAlreadyExistsDuringSignUp = "Cannot register user since the given" +
                    " credentials already exist in the system."
            fun invalidParameter(paramName: String) = "Either the provided parameter '$paramName' is" +
                    " of invalid type or not provided at all."
            const val Generic = "Unexpected error occurred! Reason: "
            const val InvalidEmail = "The e-mail provided is not valid!"
            const val InvalidPassword = "A valid password contains at least 8 characters with 1 " +
                    "uppercase, 1 lowercase, 1 numeric and 1 special character"
            const val InvalidCredentials = "Credentials entered are invalid!"
        }
        object Params {
            const val UserEmail = "email"
            const val UserPassword = "password"
        }
        const val JWTClaimEmail = "userEmail"
    }
    object NetworkClientEndpoints {
        private const val BaseURL = "http://localhost:8080"
        const val Login = BaseURL + Server.Routes.login
        const val Register = BaseURL + Server.Routes.register
    }
    object SQLDelight {
        const val DBName = "PTDatabase"
    }
    object Desktop {
        const val WindowTitle = "PlayTogether: Enjoy Media with Friends"
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
