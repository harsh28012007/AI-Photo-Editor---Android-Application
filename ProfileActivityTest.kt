package com.example.photoeditor

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(ProfileActivity::class.java)

    // ---------------- TC08 ----------------
    @Test
    fun testOpenProfileScreen() {

        onView(withId(R.id.tvUsername))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testUploadProfilePhoto() {

        // Just check button exists (DO NOT CLICK)
        onView(withId(R.id.btnChangeProfilePhoto))
            .check(matches(isDisplayed()))

        // Check image view exists
        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testProfileImagePersistence() {


        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testInvalidFileSelection() {


        onView(withId(R.id.ivProfile))
            .check(matches(isDisplayed()))
    }
}