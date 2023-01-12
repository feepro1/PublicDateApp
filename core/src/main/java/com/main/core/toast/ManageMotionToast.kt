package com.main.core.toast

import androidx.fragment.app.Fragment
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun showInfoColorToast(fragment: Fragment, text: String) {
    MotionToast.createColorToast(
        fragment.requireActivity(),
        null,
        text,
        MotionToastStyle.INFO,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        null
    )
}

fun showErrorColorToast(fragment: Fragment, text: String) {
    MotionToast.createColorToast(
        fragment.requireActivity(),
        null,
        text,
        MotionToastStyle.ERROR,
        MotionToast.GRAVITY_BOTTOM,
        MotionToast.LONG_DURATION,
        null
    )
}