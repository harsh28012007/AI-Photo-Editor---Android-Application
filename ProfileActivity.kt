package com.example.photoeditor

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var ivProfile: ImageView
    private lateinit var tvUsername: TextView

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = contentResolver.openInputStream(it)
            val file = File(filesDir, "profile_img.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            getSharedPreferences("user", MODE_PRIVATE).edit()
                .putString("profile_img", file.absolutePath).apply()
            ivProfile.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ivProfile = findViewById(R.id.ivProfile)
        tvUsername = findViewById(R.id.tvUsername)
        val btnChangePhoto = findViewById<Button>(R.id.btnChangeProfilePhoto)
        val btnContinue = findViewById<Button>(R.id.btnContinueToApp)

        // Load username
        val username = getSharedPreferences("user", MODE_PRIVATE).getString("username", "User")
        tvUsername.text = username

        // Load profile image if already set
        val imgPath = getSharedPreferences("user", MODE_PRIVATE).getString("profile_img", null)
        if (imgPath != null && File(imgPath).exists()) {
            ivProfile.setImageBitmap(BitmapFactory.decodeFile(imgPath))
        }

        btnChangePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        btnContinue.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
