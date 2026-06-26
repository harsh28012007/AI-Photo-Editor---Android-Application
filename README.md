<div align="center">

#  AI Photo Editor - Android Application

### A Modern Android Photo Editing Application Built with Kotlin

*Edit • Enhance • Save • Share • Test*

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-34A853?style=for-the-badge&logo=androidstudio&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge)
![Espresso](https://img.shields.io/badge/Espresso-FF6F00?style=for-the-badge)
![GitHub](https://img.shields.io/badge/Open%20Source-GitHub-black?style=for-the-badge&logo=github)

</div>



#  About the Project

AI Photo Editor is a native Android application developed using **Kotlin** that provides users with an intuitive platform for editing and enhancing images directly on their mobile devices.

The application combines modern Android development practices with GPU-accelerated image processing to deliver a smooth and responsive editing experience. Users can upload images from the gallery, apply filters, adjust brightness, rotate or flip images, save edited photos, and share them instantly.

To ensure reliability and maintainability, the project also includes automated testing using **JUnit** and **Espresso**, demonstrating software quality assurance practices alongside application development.




#  Project Objectives

- Develop a modern Android application using Kotlin.
- Implement real-time image editing features.
- Build a clean and user-friendly interface.
- Manage user profiles securely.
- Apply Android development best practices.
- Validate application functionality through automated testing.





#  Features

##  User Authentication & Profile

- User Registration
- User Login
- User Profile Management
- Upload Profile Picture
- Display User Information
- Account Switching
- Persistent Login using SharedPreferences



##  Image Upload

- Select Images from Gallery
- Runtime Permission Handling
- Android Activity Result API
- Efficient Bitmap Loading



##  Image Editing

### Image Transformations

- Rotate Image
- Flip Image Horizontally


### Image Adjustments

- Brightness Control


### Filters

- Original
- Grayscale
- Color Invert
- Sketch
- Gaussian Blur


## Save Images

- Save Edited Images to Device Gallery
- MediaStore API Integration
- High-Quality Image Export



##  Share Images

- Share Images using Android Share Intent



##  Profile Management

- Update Profile Picture
- Display User Details
- Manage User Session





#  Software Testing

The application includes manual and automated testing to improve reliability and maintain software quality.

### Testing Frameworks

- JUnit
- Android Espresso
- Instrumentation Testing

### Tested Functionalities

- Login Validation
- Registration
- Profile Creation
- Gallery Image Upload
- Rotate Image
- Flip Image
- Brightness Adjustment
- Image Filters
- Save Image
- Share Image
- User Navigation
- Account Management

### Testing Coverage

- Functional Testing
- UI Testing
- Automated Testing
- Manual Testing
- Integration Testing
- Navigation Testing
- Error Handling





#  Tech Stack

## Programming Language

- Kotlin

## Android Development

- Android SDK
- AndroidX
- XML Layouts
- Material Design

## Libraries

- GPUImage
- Glide

## Storage

- SharedPreferences
- MediaStore API
- FileProvider

## Background Processing

- Kotlin Coroutines

## Testing

- JUnit
- Espresso
- Android Instrumentation Testing

## Tools

- Android Studio
- Gradle





#  Project Structure

```text
AI-Photo-Editor-Android-App
│
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── AndroidManifest.xml
│   │   │   │
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── example
│   │   │   │           └── photoeditor
│   │   │   │               ├── LoginActivity.kt
│   │   │   │               ├── ProfileActivity.kt
│   │   │   │               └── MainActivity.kt
│   │   │   │
│   │   │   └── res
│   │   │       ├── layout
│   │   │       │   ├── activity_login.xml
│   │   │       │   ├── activity_profile.xml
│   │   │       │   └── activity_main.xml
│   │   │       │
│   │   │       ├── drawable
│   │   │       ├── mipmap
│   │   │       ├── values
│   │   │       │   ├── colors.xml
│   │   │       │   ├── strings.xml
│   │   │       │   ├── styles.xml
│   │   │       │   └── themes.xml
│   │   │       │
│   │   │       ├── values-night
│   │   │       └── xml
│   │   │           ├── backup_rules.xml
│   │   │           ├── data_extraction_rules.xml
│   │   │           └── file_paths.xml
│   │   │
│   │   ├── androidTest
│   │   │   └── java
│   │   │       └── com
│   │   │           └── example
│   │   │               └── photoeditor
│   │   │                   ├── LoginTest.kt
│   │   │                   ├── ProfileActivityTest.kt
│   │   │                   ├── ImageUploadTest.kt
│   │   │                   ├── ImageEditingTest.kt
│   │   │                   ├── SaveImageTest.kt
│   │   │                   ├── ShareImageTest.kt
│   │   │                   ├── AccountTest.kt
│   │   │                   └── ExampleInstrumentedTest.kt
│   │   │
│   │   └── test
│   │       └── java
│   │           └── com
│   │               └── example
│   │                   └── photoeditor
│   │                       └── ExampleUnitTest.kt
│   │
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── .gitignore
│
├── gradle
│   ├── wrapper
│   └── libs.versions.toml
│
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── .gitignore
├── README.md
└── LICENSE
```





#  Project Highlights

1. Native Android Development

2. Kotlin Programming

3. Material Design Interface

4. User Authentication

5. Profile Management

6. Gallery Integration

7. Image Editing

8. GPU Accelerated Filters

9. Brightness Adjustment

10. Save Images

11. Share Images

12. SharedPreferences

13. MediaStore API

14. Runtime Permissions

15. Kotlin Coroutines

16. Automated Testing

17. Espresso UI Testing

18. JUnit Testing






#  Skills Demonstrated

- Android Development
- Kotlin Programming
- Object-Oriented Programming
- Mobile UI Design
- Image Processing
- Android SDK
- SharedPreferences
- MediaStore API
- FileProvider
- Kotlin Coroutines
- Software Testing
- JUnit
- Espresso
- Debugging
- Problem Solving





#  What I Learned

During the development of this project I gained hands-on experience in:

- Building Android applications using Kotlin
- Implementing image processing features
- Working with Android Storage APIs
- Managing user data securely
- Optimizing application performance
- Writing automated UI tests
- Creating Unit Tests
- Debugging Android application





#  Future Enhancements

- AI Image Enhancement
- Background Removal
- Crop Tool
- Undo & Redo
- Contrast Adjustment
- Saturation Adjustment
- Dark Mode
- Firebase Cloud Backup
- GitHub Actions CI/CD Pipeline
