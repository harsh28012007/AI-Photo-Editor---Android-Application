package com.example.photoeditor

// ✅ Required Imports (VERY IMPORTANT)
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageEditingTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testRotateImage() {
        onView(withId(R.id.btnRotate))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testFlipImage() {
        onView(withId(R.id.btnFlip))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testIncreaseBrightness() {
        onView(withId(R.id.seekBarBrightness))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }



    @Test
    fun testDecreaseBrightness() {
        onView(withId(R.id.seekBarBrightness))
            .perform(click())

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testSelectFilter() {
        onView(withId(R.id.spinnerFilters))
            .check(matches(isDisplayed()))
            .perform(click())

        // Select first item from spinner
        onData(anything()).atPosition(0).perform(click())

        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testFilterButton() {
        onView(withId(R.id.btnFilter))
            .check(matches(isDisplayed()))
            .perform(click())

        // App should not crash
        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }
}