package com.brunohensel.cardgame.home.presentation.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.brunohensel.cardgame.R
import com.brunohensel.cardgame.home.datasource.remote.AvailableGamesList
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest{

    @get: Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun test_isRecyclerViewVisible_onLaunch(){
        onView(withId(R.id.rvAvailableGames)).check(matches(isDisplayed()))
        Thread.sleep(1500)
        onView(withId(R.id.rvAvailableGames)).perform( actionOnItemAtPosition<HomeAdapter.GameViewHolder>(0, click()))
        onView(withText(AvailableGamesList()[0].name)).check(matches(isDisplayed()))
    }
}