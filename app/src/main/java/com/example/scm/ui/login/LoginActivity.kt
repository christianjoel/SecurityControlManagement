package com.example.scm.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.scm.MainActivity
import com.example.scm.databinding.ActivityLoginBinding
import com.example.scm.utils.helper.AppHelper
import com.example.scm.utils.helper.DataStorage
import com.example.scm.utils.helper.Keys

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiview()
        //   decidetoopen()

    }

    public fun intiview() {

        binding.llLogin.setOnClickListener {

/*            if (binding.edUserid.text.toString().isNotEmpty()) {

            } else {
                AppHelper.showToast(this, "Please enter a valid userid")
            }
            if (binding.edPin.text.toString().isNotEmpty()) {

            } else {
                AppHelper.showToast(this, "Please enter a valid password")
            }
            if (binding.edUserid.text.toString() == "SEC001" && binding.edPin.text.toString() == "1234") {
                DataStorage(this).saveString(Keys.userName, "SEC001")
                AppHelper.onNextPage(this, null, MainActivity::class.java)
                finishAffinity()
            } else {
                AppHelper.showToast(this, "Please enter a valid credentials")
            }*/
            AppHelper.onNextPage(this, null, MainActivity::class.java)

        }
    }

    private fun decidetoopen() {
        if (DataStorage(this).getString(Keys.userName).toString().isNotEmpty()) {
            DataStorage(this).saveString(Keys.userName, "SEC001")
            AppHelper.onNextPage(this, null, MainActivity::class.java)
            finishAffinity()
        } else {

        }
    }
}