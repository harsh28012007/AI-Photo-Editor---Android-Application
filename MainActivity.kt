package com.example.photoeditor

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var filterSpinner: Spinner
    private lateinit var btnSelect: Button
    private lateinit var btnRotate: Button
    private lateinit var btnFlip: Button
    private lateinit var btnFilter: Button
    private lateinit var btnSave: Button
    private lateinit var btnShare: Button
    private lateinit var btnSwitchAccount: Button

    private lateinit var imgAccountProfile: ImageView
    private lateinit var txtAccountName: TextView

    private var imageUri: Uri? = null
    private var lastBitmap: Bitmap? = null

    private lateinit var gpuImage: GPUImage

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) pickImageFromGallery()
        else Toast.makeText(this, "Permission denied, cannot select image", Toast.LENGTH_SHORT).show()
    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            Glide.with(this).load(it).into(imageView)
            lifecycleScope.launch {
                lastBitmap = withContext(Dispatchers.IO) {
                    MediaStore.Images.Media.getBitmap(contentResolver, it)
                }
                gpuImage.setImage(lastBitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        imageView = findViewById(R.id.imageView)
        brightnessSeekBar = findViewById(R.id.seekBarBrightness)
        filterSpinner = findViewById(R.id.spinnerFilters)
        btnSelect = findViewById(R.id.btnSelect)
        btnRotate = findViewById(R.id.btnRotate)
        btnFlip = findViewById(R.id.btnFlip)
        btnFilter = findViewById(R.id.btnFilter)
        btnSave = findViewById(R.id.btnSave)
        btnShare = findViewById(R.id.btnShare)
        btnSwitchAccount = findViewById(R.id.btnSwitchAccount)

        imgAccountProfile = findViewById(R.id.imgAccountProfile)
        txtAccountName = findViewById(R.id.txtAccountName)

        gpuImage = GPUImage(this)

        val prefs = getSharedPreferences("user", MODE_PRIVATE)
        val username = prefs.getString("username", "User") ?: "User"
        txtAccountName.text = username
        val profileImg = prefs.getString("profile_img", null)
        if (profileImg != null && File(profileImg).exists()) {
            imgAccountProfile.setImageBitmap(BitmapFactory.decodeFile(profileImg))
        }

        btnSwitchAccount.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        txtAccountName.setOnClickListener {
            showAccountDetailsDialog(username)
        }

        btnSelect.setOnClickListener {
            if (hasStoragePermission()) pickImageFromGallery()
            else requestStoragePermission()
        }

        brightnessSeekBar.max = 200
        brightnessSeekBar.progress = 100

        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                lastBitmap?.let {
                    val brightness = (progress - 100) / 100.0f
                    gpuImage.setImage(it)
                    gpuImage.setFilter(GPUImageBrightnessFilter(brightness))
                    imageView.setImageBitmap(gpuImage.bitmapWithFilterApplied)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        val filters = listOf("None", "Grayscale", "Invert", "Sketch", "Gaussian Blur")
        filterSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, filters)
        filterSpinner.setSelection(0)

        filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                lastBitmap?.let {
                    val filter = when (filters[position]) {
                        "None" -> GPUImageFilter()
                        "Grayscale" -> GPUImageGrayscaleFilter()
                        "Invert" -> GPUImageColorInvertFilter()
                        "Sketch" -> GPUImageSketchFilter()
                        "Gaussian Blur" -> GPUImageGaussianBlurFilter()
                        else -> GPUImageFilter()
                    }
                    gpuImage.setImage(it)
                    gpuImage.setFilter(filter)
                    imageView.setImageBitmap(gpuImage.bitmapWithFilterApplied)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnRotate.setOnClickListener {
            lastBitmap = lastBitmap?.let { rotateBitmap(it, 90f) }
            lastBitmap?.let { imageView.setImageBitmap(it) }
        }

        btnFlip.setOnClickListener {
            lastBitmap = lastBitmap?.let { flipBitmap(it) }
            lastBitmap?.let { imageView.setImageBitmap(it) }
        }

        btnFilter.setOnClickListener {
            // Add additional filter actions if required
        }

        btnSave.setOnClickListener {
            val editedBitmap = gpuImage.bitmapWithFilterApplied
            if (editedBitmap != null) {
                saveImageToGallery(editedBitmap)
                saveEditedPhotoToAccountPerUser(editedBitmap, username)
                Toast.makeText(this, "Photo saved successfully", Toast.LENGTH_SHORT).show()
            }
        }

        btnShare.setOnClickListener {
            val bitmap = gpuImage.bitmapWithFilterApplied
            if (bitmap != null) {
                shareImage(bitmap)
            } else {
                Toast.makeText(this, "No image available to share", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAccountDetailsDialog(username: String) {
        val prefs = getSharedPreferences("user", MODE_PRIVATE)

        val name = prefs.getString("name", "Name not set")
        val dob = prefs.getString("dob", "DOB not set")
        val email = prefs.getString("email", "Email not set")
        val photosKey = "${username}_photos"
        val userPhotoPaths = prefs.getStringSet(photosKey, emptySet()) ?: emptySet()

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val headerText = "Name: $name\nDOB: $dob\nEmail: $email\nUsername: $username"
        val tvHeader = TextView(this).apply {
            text = headerText
            setPadding(0, 0, 0, 16)
        }
        container.addView(tvHeader)

        val scrollView = ScrollView(this)
        val photosContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        if (userPhotoPaths.isEmpty()) {
            photosContainer.addView(TextView(this).apply { text = "No photos available." })
        } else {
            userPhotoPaths.forEach { path ->
                val file = File(path)
                if (file.exists()) {
                    val imageViewItem = ImageView(this).apply {
                        val bmp = BitmapFactory.decodeFile(path)
                        setImageBitmap(bmp)
                        adjustViewBounds = true
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            (200 * resources.displayMetrics.density).toInt()
                        ).apply { bottomMargin = 8 }
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        setOnClickListener {
                            val iv = ImageView(this@MainActivity).apply {
                                setImageBitmap(bmp)
                            }
                            AlertDialog.Builder(this@MainActivity)
                                .setView(iv)
                                .setPositiveButton("Close", null)
                                .show()
                        }
                    }
                    photosContainer.addView(imageViewItem)
                }
            }
        }

        scrollView.addView(photosContainer)
        container.addView(scrollView)

        AlertDialog.Builder(this)
            .setTitle("Account Photos")
            .setView(container)
            .setPositiveButton("Close", null)
            .show()
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val filename = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/photoeditor")
        }
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            contentResolver.openOutputStream(uri)?.use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            Toast.makeText(this, "Saved to gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveEditedPhotoToAccountPerUser(bitmap: Bitmap, username: String) {
        try {
            val userDir = File(filesDir, username)
            if (!userDir.exists()) userDir.mkdirs()
            val filename = "edited_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
            val file = File(userDir, filename)
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            val prefs = getSharedPreferences("user", MODE_PRIVATE)
            val photosKey = "${username}_photos"
            val existingSet = prefs.getStringSet(photosKey, mutableSetOf()) ?: mutableSetOf()
            val list = existingSet.toMutableSet()
            list.add(file.absolutePath)
            prefs.edit().putStringSet(photosKey, list).apply()
            Toast.makeText(this, "Photo saved to your account", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving photo: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

    private fun shareImage(bitmap: Bitmap) {
        val file = File(cacheDir, "share_image.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        val shareUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", file)
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, shareUri)
            type = "image/jpeg"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    private fun hasStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun pickImageFromGallery() {
        pickImageLauncher.launch("image/*")
    }

    private fun rotateBitmap(src: Bitmap, degree: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }

    private fun flipBitmap(src: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.preScale(-1f, 1f)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }
}
