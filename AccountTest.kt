package com.example.photoeditor

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountTest {

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // ---------------- TC25 ----------------
    @Test
    fun testSwitchAccount() {

        onView(withId(R.id.btnSwitchAccount))
            .perform(click())


        onView(withId(R.id.imageView))
            .check(doesNotExist())
    }

    // ---------------- TC26 ----------------
    @Test
    fun testDataClearedAfterSwitch() {

        onView(withId(R.id.btnSwitchAccount))
            .perform(click())


        onView(withId(R.id.imageView))
            .check(doesNotExist())
    }
}