package com.example.scm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.scm.databinding.ActivityMainBinding
import com.example.scm.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nav_host_fragment: FragmentContainerView
    private lateinit var navHostFragment: NavHostFragment


    companion object {
        lateinit var navController: NavController
        lateinit var bottom_navigation: BottomNavigationView
        var fragmmentname = "HomeFragment"
        var homeFragment: HomeFragment = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiview()
    }

    public fun intiview() {
        nav_host_fragment = findViewById(R.id.nav_host_fragment)
        initibottomnav()
    }

    private fun initibottomnav() {
        try {
            navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            bottom_navigation = findViewById(R.id.bottom_navigation)
            bottom_navigation.setupWithNavController(navController)

        } catch (type: Exception) {

        }
    }
}