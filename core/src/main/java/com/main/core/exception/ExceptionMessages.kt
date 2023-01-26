package com.main.core.exception

object ExceptionMessages {
    //Basic
    const val INTERNET_IS_UNAVAILABLE = "Network error.."
    const val USER_WAS_NOT_FOUND = "User was not found"
    const val MESSAGE_WAS_NOT_FOUND = "Message was not found"
    const val MESSAGE_IS_EMPTY = "Message is empty"
    const val RECEIVER_UID_IS_EMPTY = "Receiver UID is empty"
    const val SENDER_UID_IS_EMPTY = "Sender UID is empty"
    const val PASSWORD_IS_TOO_SHORT = "Password is too short"
    const val PASSWORD_DOES_NOT_CONSIST_A_CAPITAL_LETTER = "Password does not consist a capital letter"
    const val PASSWORD_IS_EMPTY = "Password is empty"
    const val EMAIL_IS_EMPTY = "Email is empty"
    const val FIRST_NAME_IS_EMPTY = "First name is empty"
    const val LAST_NAME_IS_EMPTY = "Last name is empty"
    const val CONFIRM_PASSWORD_IS_EMPTY = "Confirm password is empty"
    const val PASSWORDS_DO_NOT_MATCH = "Passwords do not match"
    //Firebase
    const val PASSWORD_IS_INCORRECT = "The password is invalid or the user does not have a password."
    const val EMAIL_WAS_NOT_FOUND = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val EMAIL_ADDRESS_IS_INCORRECT = "The email address is badly formatted."
    const val EMAIL_ADDRESS_IS_BUSY = "The email address is already in use by another account."
    //Firebase UI
    const val PASSWORD_IS_INCORRECT_UI = "Password is invalid"
    const val EMAIL_WAS_NOT_FOUND_UI = "User was not found"
    const val EMAIL_ADDRESS_IS_INCORRECT_UI = "The email address is incorrect"
    const val EMAIL_ADDRESS_IS_BUSY_UI = "The email address is busy"
}