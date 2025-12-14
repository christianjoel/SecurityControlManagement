package com.example.scm.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.scm.MainActivity
import com.example.scm.R
import com.example.scm.databinding.ActivitySplashBinding
import com.example.scm.ui.login.LoginActivity
import com.example.scm.utils.helper.AppHelper
import com.example.scm.utils.helper.DataStorage

class SplashActivity :AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var dataStorage: DataStorage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySplashBinding.inflate(layoutInflater)
        dataStorage = DataStorage(this)
        //dataStorage.saveBoolean(Keys.IS_INTRO_SHOWN, false)
        initView()
    }
    private fun initView() {
        //    throw RuntimeException("Test Crash"); // Force a crash
        decideAndOpen()

    }

    fun decideAndOpen() {

        val bundle = Bundle()
        AppHelper.onNextPage(this, bundle, LoginActivity::class.java)
        finish()
    }

    fun AnimViews() {
        Handler(Looper.myLooper()!!).postDelayed(object : Runnable {
            override fun run() {
                //  callApi()
            }
        }, 100)
    }

}