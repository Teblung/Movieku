package com.example.movieku.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieku.R
import com.example.movieku.ui.favorite.popular.FavPopMovieFragment
import com.example.movieku.ui.favorite.upcoming.FavUpMovieFragment

class SectionsFavoritePagerAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TAB_TITLE = intArrayOf(R.string.upcoming, R.string.popular)
    }

    override fun getCount(): Int = TAB_TITLE.size

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FavUpMovieFragment()
            1 -> FavPopMovieFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence = context
        .resources.getString(TAB_TITLE[position])
}