package com.example.pam_projekt
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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var menuLoginItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_logo)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        drawerLayout = findViewById(R.id.drawerLayout)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController


        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_login -> {
                    if (menuItem.title == getString(R.string.menu_logout)) {
                        signOut()
                    } else {
                        navigateToSignInFragment()
                    }
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


        toolbar.setNavigationOnClickListener {
            openDrawer()
        }

        navController.addOnDestinationChangedListener { _, destination: NavDestination, _ ->
            val menuItem = navigationView.menu.findItem(destination.id)
            menuItem?.isChecked = true
        }


        firebaseAuth = FirebaseAuth.getInstance()


        menuLoginItem = navigationView.menu.findItem(R.id.menu_login)


        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            updateMenuLoginItem(true)

            navController.navigate(R.id.homeFragment)
        } else {
            updateMenuLoginItem(false)
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

    private fun navigateToSignInFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signInFragment, true)
            .build()
        navController.navigate(R.id.signInFragment, null, navOptions)
    }

    private fun updateMenuLoginItem(isLoggedIn: Boolean) {
        menuLoginItem.title = if (isLoggedIn) getString(R.string.menu_logout) else getString(R.string.menu_login)
    }


    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signIn(email, password)
                } else {
                    showToast("Sign-up failed: ${task.exception?.message}")
                }
            }
    }
    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateMenuLoginItem(true)
                    navController.navigate(R.id.homeFragment)
                } else {
                    showToast("Sign-in failed: ${task.exception?.message}")
                }
            }
    }
    fun signOut() {
        firebaseAuth.signOut()
        updateMenuLoginItem(false)
        navigateToSignInFragment()
        showToast("Signed out successfully.")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
