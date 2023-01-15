package com.main.dating.domain

import android.view.animation.AccelerateInterpolator
import com.yuyakaido.android.cardstackview.*

interface ProvideAnimationsSettings {

    fun createAnimationsSettingsForLike(
        cardStackView: CardStackView,
        layoutManager: CardStackLayoutManager
    )

    fun createAnimationsSettingsForDislike(
        cardStackView: CardStackView,
        layoutManager: CardStackLayoutManager
    )

    class Base : ProvideAnimationsSettings {

        override fun createAnimationsSettingsForLike(
            cardStackView: CardStackView,
            layoutManager: CardStackLayoutManager
        ) {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        override fun createAnimationsSettingsForDislike(
            cardStackView: CardStackView,
            layoutManager: CardStackLayoutManager
        ) {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            layoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
    }
}