package com.example.intorn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.intorn.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.loginButton.setOnClickListener {

            val loginUsername = binding.loginUsername.text.toString()
            val loginPassword = binding.LoginPassword.text.toString()
            loginDatabase(loginUsername,loginPassword)

        }
    }

    private fun loginDatabase(username: String, password: String) {
        val userExists = databaseHelper.readUser(username, password)
        if (userExists) {

            Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("loginUserName", username)
            startActivity(intent)
            finish()

        }else{

            Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
        }
    }
}