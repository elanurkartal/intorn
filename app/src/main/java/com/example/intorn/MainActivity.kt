package com.example.intorn

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.intorn.databinding.ActivityMainBinding
import com.example.intorn.staff.userFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)

        sharedPreferences=getSharedPreferences("UserInfo", MODE_PRIVATE)
        editor=sharedPreferences.edit()
        val loginUser = sharedPreferences.getString("user_name","")

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
            val bundle = Bundle()
         //   bundle.putString("loginUser",loginUser)

            navController.navigate(R.id.homeFragment, bundle)
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

