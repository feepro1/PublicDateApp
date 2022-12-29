package com.main.swaplike.presentation.ui

import android.os.Bundle
import com.main.core.base.BaseActivity
import com.main.swaplike.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}