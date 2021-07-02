package com.example.movieku.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieku.databinding.ActivityHomeBinding

class FavoriteActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.run {
            val sectionsPagerAdapter =
                SectionsFavoritePagerAdapter(this@FavoriteActivity, supportFragmentManager)
            viewPagerHome.adapter = sectionsPagerAdapter
            tabsHome.setupWithViewPager(viewPagerHome)

            supportActionBar?.elevation = 0f
        }
    }
}