package com.example.photoeditor

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)


    @Test
    fun testEmptyFields() {
        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText("Please fill all fields"))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testPartialInput() {
        onView(withId(R.id.etName))
            .perform(typeText("John"))

        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        onView(withText("Please fill all fields"))
            .check(matches(isDisplayed()))
    }


    @Test
    fun testValidInput() {
        onView(withId(R.id.etName)).perform(typeText("John"))
        onView(withId(R.id.etDob)).perform(typeText("01-01-2000"))
        onView(withId(R.id.etEmail)).perform(typeText("john@mail.com"))
        onView(withId(R.id.etUsername)).perform(typeText("john123"))

        closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())

        // Check screen loaded
        onView(withId(android.R.id.content))
            .check(matches(isDisplayed()))
    }
}