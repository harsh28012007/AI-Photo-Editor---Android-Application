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
class ShareImageTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testShareImage() {

        onView(withId(R.id.btnShare))
            .check(matches(isDisplayed()))

        // Check app is stable
        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testShareWithoutImage() {

        // Expect warning (not implemented → FAIL)
        onView(withText("No image selected"))
            .check(matches(isDisplayed()))
    }
}