package com.example.movieku.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.movieku.R
import com.example.movieku.databinding.ActivityHomeBinding
import com.example.movieku.ui.favorite.FavoriteActivity

class HomeActivity : AppCompatActivity() {
    private var menu: Menu? = null

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
                SectionsHomePagerAdapter(this@HomeActivity, supportFragmentManager)
            viewPagerHome.adapter = sectionsPagerAdapter
            tabsHome.setupWithViewPager(viewPagerHome)

            supportActionBar?.elevation = 0f
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_bookmark) {
            startActivity(Intent(this, FavoriteActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}