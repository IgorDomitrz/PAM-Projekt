package com.example.pam_projekt

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.pam_projekt.databinding.ActivityMainBinding
import com.example.pam_projekt.ui.login.SignInFragment
import com.example.pam_projekt.ui.login.SignUpFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Drawer layout
        drawerLayout = binding.drawerLayout

        // Navigation controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up navigation view
        navigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_login -> {
                   navigateToSignInFragment()
                    closeDrawer()
                    true

                }

                R.id.menu_basket -> {
                    // Handle basket menu click
                    closeDrawer()
                    true
                }
                R.id.menu_settings -> {
                    // Handle settings menu click
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

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if a user is already signed in
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            // User is signed in, navigate to the desired destination
            navController.navigate(R.id.homeFragment)
        }
    }

    private fun navigateToSignInFragment() {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.signInFragment, true)
                .build()
            navController.navigate(R.id.signInFragment, null, navOptions)
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

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    showToast("Sign-in failed: ${task.exception?.message}")
                }
            }
    }

    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    showToast("Sign-up failed: ${task.exception?.message}")
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
        navigateToSignIn()
    }

    private fun navigateToHome() {
        navController.navigate(R.id.homeFragment)
    }

    private fun navigateToSignIn() {
        navController.navigate(R.id.signInFragment)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
