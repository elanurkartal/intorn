package com.example.intorn

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.intorn.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = binding.drawerLayout


        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.linearLayoutSettingsActivityStaff.setOnClickListener {
            navController.navigate(R.id.staffFragment)
            close()
        }
        binding.masterDataLinear.setOnClickListener {
            navController.navigate(R.id.dataFragment)
            close()
        }
        binding.linearLayoutSettingActivityReport.setOnClickListener {
            navController.navigate((R.id.reportFragment))
            close()
        }
        binding.linearLayoutSettingActivity.setOnClickListener {
            navController.navigate((R.id.settingsFragment))
            close()
        }
        binding.linearLayoutHomeActivity.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            close()
        }

    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        lateinit var navController: NavController
    }

    private fun close() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }
}

