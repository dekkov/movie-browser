package edu.oregonstate.cs492.finalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import edu.oregonstate.cs492.finalproject.R
import edu.oregonstate.cs492.finalproject.data.TMDBAPI.TMDBMovieService

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var appBarConfig: AppBarConfiguration
    private val service = TMDBMovieService.create()
    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge() // "Seamless" looking app without top info bar

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(navController.graph)
        val appBar: MaterialToolbar = findViewById(R.id.top_app_bar)
        setSupportActionBar(appBar)
        setupActionBarWithNavController(navController, appBarConfig)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNav.setupWithNavController(navController)

        //Fixes the enableEdgeToEdge() padding, remove if not using that
        bottomNav.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }
}