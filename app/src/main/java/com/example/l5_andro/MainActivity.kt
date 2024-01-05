package com.example.l5_andro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.l5_andro.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment:NavHostFragment
    lateinit var navController: NavController
    lateinit var navView: NavigationView
    lateinit var toolbarMain: androidx.appcompat.widget.Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNav: BottomNavigationView
    lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarMain = binding.toolbar1
        setSupportActionBar(toolbarMain)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.sfcontainer) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout=binding.drawerLayout
        navView = binding.navView
        bottomNav = binding.bottomMenu


        appBarConfig =
            AppBarConfiguration(setOf(R.id.image_swipe_frag,R.id.main_frag,R.id.list5Fragment), drawerLayout)
        // old config:
        // AppBarConfiguration(setOf(R.id.swipe_frag,R.id.main_frag,R.id.list_frag), drawerLayout)
        setupActionBarWithNavController(navController,appBarConfig)
        navView.setupWithNavController(navController)
        bottomNav.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}