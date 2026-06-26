package com.example.photoeditor

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etDob: EditText
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etName = findViewById(R.id.etName)
        etDob = findViewById(R.id.etDob)
        etEmail = findViewById(R.id.etEmail)
        etUsername = findViewById(R.id.etUsername)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val name = etName.text.toString().trim()
            val dob = etDob.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val username = etUsername.text.toString().trim()

            if (name.isEmpty() || dob.isEmpty() || email.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val prefs = getSharedPreferences("user", MODE_PRIVATE)
                prefs.edit().apply {
                    putString("name", name)
                    putString("dob", dob)
                    putString("email", email)
                    putString("username", username)
                    apply()
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
