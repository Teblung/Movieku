package com.example.movieku.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.movieku.R
import com.example.movieku.utils.DummyData
import com.example.movieku.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

//    NOTE
//    Bookmark First On Space Jam: A New Legacy (Upcoming Movie) On App
//    Because this instrument test running first on deleteUpMovieBookmarks

    private val dummyUpMovie = DummyData.generateUpMovies()
    private val dummyPopMovie = DummyData.generatePopMovies()

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadUpMovies() {
        Espresso.onView(withId(R.id.rv_up_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_up_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyUpMovie.size))
    }

    @Test
    fun loadDetailUpMovie() {
        Espresso.onView(withId(R.id.rv_up_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(withText(dummyUpMovie[0].title)))

        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.withText("Vote Count\n${dummyUpMovie[0].voteCount}")))

        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.withText("Popularity\n${dummyUpMovie[0].popularity}")))

        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.withText("Original Language\n${dummyUpMovie[0].originalLanguage}")))

        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText("Overview\n${dummyUpMovie[0].overview}")))

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share)).perform(ViewActions.click())
    }

    @Test
    fun loadPopMovies() {
        Espresso.onView(ViewMatchers.withText("POPULAR")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_pop_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_pop_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyPopMovie.size))
    }

    @Test
    fun loadDetailPopMovie() {
        Espresso.onView(ViewMatchers.withText("POPULAR")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_pop_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_pop_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyPopMovie.size))
        Espresso.onView(withId(R.id.rv_pop_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(withText(dummyPopMovie[0].title)))

        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.withText("Vote Count\n${dummyPopMovie[0].voteCount}")))

        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.withText("Popularity\n${dummyPopMovie[0].popularity}")))

        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.withText("Original Language\n${dummyPopMovie[0].originalLanguage}")))

        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText("Overview\n${dummyPopMovie[0].overview}")))

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share)).perform(ViewActions.click())
    }

    @Test
    fun loadUpMovieBookmarks() {
        Espresso.onView(withId(R.id.rv_up_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(ViewMatchers.withId(R.id.action_bookmark)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_fav_up_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_fav_up_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(withText(dummyUpMovie[0].title)))

        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.withText("Vote Count\n${dummyUpMovie[0].voteCount}")))

        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.withText("Popularity\n${dummyUpMovie[0].popularity}")))

        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.withText("Original Language\n${dummyUpMovie[0].originalLanguage}")))

        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText("Overview\n${dummyUpMovie[0].overview}")))

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share)).perform(ViewActions.click())
    }

    @Test
    fun loadPopMovieBookmarks() {
        Espresso.onView(ViewMatchers.withText("POPULAR")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_pop_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(ViewMatchers.withId(R.id.action_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("POPULAR")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_fav_pop_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_fav_pop_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_title))
            .check(ViewAssertions.matches(withText(dummyPopMovie[0].title)))

        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_vote_count))
            .check(ViewAssertions.matches(ViewMatchers.withText("Vote Count\n${dummyPopMovie[0].voteCount}")))

        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.tv_detail_popularity))
            .check(ViewAssertions.matches(ViewMatchers.withText("Popularity\n${dummyPopMovie[0].popularity}")))

        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_original_language))
            .check(ViewAssertions.matches(ViewMatchers.withText("Original Language\n${dummyPopMovie[0].originalLanguage}")))

        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_detail_overview))
            .check(ViewAssertions.matches(ViewMatchers.withText("Overview\n${dummyPopMovie[0].overview}")))

        Espresso.onView(ViewMatchers.withId(R.id.img_detail_poster))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_share)).perform(ViewActions.click())
    }

    @Test
    fun deleteUpMovieBookmarks() {
        Espresso.onView(ViewMatchers.withId(R.id.action_bookmark)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_fav_up_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.tv_notif_favorite_up_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun deletePopMovieBookmarks() {
        Espresso.onView(ViewMatchers.withId(R.id.action_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("POPULAR")).perform(ViewActions.click())
        Espresso.onView(withId(R.id.rv_fav_pop_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.cv_detail_bookmark)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.tv_notif_favorite_pop_movie))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}