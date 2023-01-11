package com.main.core.toast

import androidx.fragment.app.Fragment
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun showColorToast(fragment: Fragment, text: String) {
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