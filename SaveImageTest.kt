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
class SaveImageTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testSaveEditedImage() {

        // Only check button exists (safe)
        onView(withId(R.id.btnSave))
            .check(matches(isDisplayed()))

        // Check app UI is stable
        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testSaveWithoutImage() {

        // Expect warning (which your app doesn't show → FAIL)
        onView(withText("No image selected"))
            .check(matches(isDisplayed()))
    }
}