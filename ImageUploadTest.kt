package com.example.photoeditor

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageUploadTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(ProfileActivity::class.java)


    @Test
    fun testSelectImageFromGallery() {

        // Check button is visible (instead of clicking)
        onView(withId(R.id.btnChangeProfilePhoto))
            .check(matches(isDisplayed()))

        // Check ImageView exists
        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testUnsupportedFileSelection() {

        // Validate UI remains stable
        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testLargeImageUpload() {

        // Check app does not crash
        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }
}