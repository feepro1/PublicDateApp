package com.main.core.exception

abstract class ApplicationException(message: String): Exception(message)

class EmailException(message: String): ApplicationException(message)

class PasswordException(message: String): ApplicationException(message)

class FirstNameException(message: String): ApplicationException(message)

class LastNameException(message: String): ApplicationException(message)

class ConfirmPasswordException(message: String): ApplicationException(message)

class NetworkException(message: String): ApplicationException(message)