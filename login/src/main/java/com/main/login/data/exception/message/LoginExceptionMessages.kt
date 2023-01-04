package com.main.login.data.exception.message

object LoginExceptionMessages {
    //Firebase
    const val PASSWORD_IS_INCORRECT = "The password is invalid or the user does not have a password."
    const val EMAIL_WAS_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val EMAIL_ADDRESS_IS_INCORRECT = "The email address is badly formatted."
    //Firebase UI
    const val PASSWORD_IS_INCORRECT_UI = "Password is invalid"
    const val EMAIL_WAS_NOT_FOUND_UI = "User was not found"
    const val EMAIL_ADDRESS_IS_INCORRECT_UI = "The email address is incorrect"
    //Application
    const val PASSWORD_IS_TOO_SHORT = "Password is too short"
    const val PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER = "Password does not consist a capital letter"
    const val PASSWORD_IS_EMPTY = "Password is empty"
    const val EMAIL_IS_EMPTY = "Email is empty"
}