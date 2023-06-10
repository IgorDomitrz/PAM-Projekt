package com.example.pam_projekt

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Drawer layout
        drawerLayout = findViewById(R.id.drawerLayout)

        // Navigation controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up navigation view
        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_login -> {
                    navController.navigate(R.id.loginFragment)
                    closeDrawer()
                    true
                }
                R.id.menu_basket -> {
                    navController.navigate(R.id.basketFragment)
                    closeDrawer()
                    true
                }
                R.id.menu_settings -> {
                    navController.navigate(R.id.settingsFragment)
                    closeDrawer()
                    true
                }
                else -> false
            }
        }

        // Set up click listener for the hamburger icon in the toolbar
        toolbar.setNavigationOnClickListener {
            openDrawer()
        }

        // Set up destination changed listener to update selected item in navigation view
        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val menuItem = navigationView.menu.findItem(destination.id)
            menuItem?.isChecked = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                openDrawer()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
        } else if (navController.currentDestination?.id != R.id.homeFragment) {
            navController.navigateUp()
        } else {
            super.onBackPressed()
        }
    }


    private fun openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}
