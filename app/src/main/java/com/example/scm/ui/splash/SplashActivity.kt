package com.example.scm.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.scm.MainActivity
import com.example.scm.R
import com.example.scm.databinding.ActivitySplashBinding
import com.example.scm.utils.helper.AppHelper
import com.example.scm.utils.helper.DataStorage

class SplashActivity :AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var dataStorage: DataStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySplashBinding.inflate(layoutInflater)
        dataStorage = DataStorage(this)
        initView()
    }
    private fun initView() {
        decideAndOpen()
    }

    fun decideAndOpen() {
        val bundle = Bundle()
        AppHelper.onNextPage(this, bundle, MainActivity::class.java)
        finish()
    }
}
