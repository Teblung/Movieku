package com.example.movieku.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.movieku.R
import com.example.movieku.databinding.ActivitySplashBinding
import com.example.movieku.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val twoSecond: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.run {
            val handler = Handler()
            val animUp = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.slide_up)
            imgSplash.startAnimation(animUp)
            handler.postDelayed({
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }, twoSecond)
        }
    }
}