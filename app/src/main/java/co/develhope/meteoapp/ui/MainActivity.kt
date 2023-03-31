package co.develhope.meteoapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import co.develhope.meteoapp.Data
import co.develhope.meteoapp.R
import co.develhope.meteoapp.databinding.ActivityMainBinding
import co.develhope.meteoapp.networking.domainmodel.HomeCardInfo
import co.develhope.meteoapp.ui.error.ErrorFragment
import co.develhope.meteoapp.ui.tomorrow.TomorrowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val navController = navHostFragment?.findNavController()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_oggi,
                R.id.navigation_domani,
                R.id.navigation_search
            )
        )
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
        if (navController != null) {
            navView.setupWithNavController(navController)
            navView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.navigation_home -> {
                        if (navController.popBackStack());
                        true
                    }
                    else -> {
                        it.onNavDestinationSelected(navController)
                    }
                }
            }
        } else {
            Log.d(
                "MainActivity", "Error"
            )
        }


    }
    fun showBottomNavigation(show: Boolean) {
        if (show) {
            binding.navView.visibility = View.VISIBLE
        } else {
            binding.navView.visibility = View.GONE
        }
    }
}