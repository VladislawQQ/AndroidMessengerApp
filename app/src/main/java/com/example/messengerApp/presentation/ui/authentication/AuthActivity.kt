package com.example.messengerApp.presentation.ui.authentication

import android.os.Bundle
import com.example.messengerApp.presentation.base.BaseActivity
import com.example.messengerApp.databinding.ActivityAuthBinding
import com.example.messengerApp.domain.repository.SharedPrefSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>(ActivityAuthBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPrefSource.init(this)
    }
}