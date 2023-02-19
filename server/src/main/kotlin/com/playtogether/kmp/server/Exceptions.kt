package com.playtogether.kmp.server

import com.playtogether.kmp.data.util.Constants

open class PTException(val errorMessage: String) : Exception(errorMessage)

object UserNotFoundException : PTException(Constants.Server.Exceptions.UserNotFound)

object UserAlreadyExistsDuringSignUpException : PTException(Constants.Server.Exceptions.UserAlreadyExistsDuringSignUp)

class InvalidParameterException(paramName: String) : PTException(Constants.Server.Exceptions.invalidParameter(paramName))

object InvalidEmailException : PTException(Constants.Server.Exceptions.InvalidEmail)

object InvalidPasswordException : PTException(Constants.Server.Exceptions.InvalidPassword)

object InvalidCredentialsException : PTException(Constants.Server.Exceptions.InvalidCredentials)

class IllegalEnvironmentVariableException(variableName: String) :
    PTException(Constants.Server.Exceptions.illegalEnvironmentVariable(variableName))
