package com.example.intorn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)

        val loginUser = intent.getStringExtra("loginUserName")

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
            if(databaseHelper.getUserRole(loginUser!!)=="Manager") {
                navController.navigate(R.id.staffFragment)
            }
            else{
                Toast.makeText(this,"You can't access there",Toast.LENGTH_SHORT).show()
            }
            close()
        }
        binding.masterDataLinear.setOnClickListener {
            if(databaseHelper.getUserRole(loginUser!!)=="Manager") {
                navController.navigate(R.id.dataFragment)
            }
            else{
                Toast.makeText(this,"You can't access there",Toast.LENGTH_SHORT).show()
            }
            close()
        }
        binding.linearLayoutSettingActivityReport.setOnClickListener {
            if(databaseHelper.getUserRole(loginUser!!)=="Manager") {
                navController.navigate(R.id.reportFragment)
            }
            else{
                Toast.makeText(this,"You can't access there",Toast.LENGTH_SHORT).show()
            }
            close()
        }
        binding.linearLayoutSettingActivity.setOnClickListener {
            if(databaseHelper.getUserRole(loginUser!!)=="Manager") {
                navController.navigate(R.id.settingsFragment)
            }
            else{
                Toast.makeText(this,"You can't access there",Toast.LENGTH_SHORT).show()
            }
            close()
        }
        binding.linearLayoutHomeActivity.setOnClickListener {
            navController.navigate(R.id.homeFragment)
            close()
        }
        binding.linearLayoutExit.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
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

