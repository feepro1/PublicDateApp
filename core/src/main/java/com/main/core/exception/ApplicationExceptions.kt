package com.main.core.exception

abstract class ApplicationException(message: String): Exception(message)

class EmailException(message: String): ApplicationException(message)

class PasswordException(message: String): ApplicationException(message)

class UsernameException(message: String): ApplicationException(message)