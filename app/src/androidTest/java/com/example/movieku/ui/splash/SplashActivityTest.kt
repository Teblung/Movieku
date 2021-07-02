package com.example.movieku.ui.splash

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.movieku.R
import com.example.movieku.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class SplashActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(SplashActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadSplash() {
        Espresso.onView(ViewMatchers.withId(R.id.imgSplash))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}