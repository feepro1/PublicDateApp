package com.main.register.data.exception.message

object RegisterExceptionMessages {
    //Firebase
    const val EMAIL_ADDRESS_IS_INCORRECT = "The email address is badly formatted."
    //Firebase UI
    const val EMAIL_ADDRESS_IS_INCORRECT_UI = "The email address is incorrect"
    //Application
    const val PASSWORD_IS_TOO_SHORT = "Password is too short"
    const val PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER = "Password does not consist a capital letter"
    const val PASSWORD_IS_EMPTY = "Password is empty"
    const val EMAIL_IS_EMPTY = "Email is empty"
    const val FIRST_NAME_IS_EMPTY = "First name is empty"
    const val LAST_NAME_IS_EMPTY = "Last name is empty"
    const val CONFIRM_PASSWORD_IS_EMPTY = "Confirm password is empty"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords do not match"
}