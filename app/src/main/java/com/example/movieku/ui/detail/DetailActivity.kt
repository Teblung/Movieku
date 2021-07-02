package com.example.movieku.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movieku.R
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_ITEM
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_TYPE
import com.example.movieku.constant.AppConstant.INTENT.IMAGE_BASE_URL
import com.example.movieku.constant.AppConstant.INTENT.TAG_POP_MOVIE
import com.example.movieku.constant.AppConstant.INTENT.TAG_UP_MOVIE
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.databinding.ActivityDetailBinding
import com.example.movieku.viewmodel.ViewModelFactory
import com.example.movieku.vo.Status

class DetailActivity : AppCompatActivity(), DetailActivityCallback {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]
        val extras = intent.extras
        if (extras != null) {
            val extraItem = extras.getInt(EXTRA_ITEM)
            val extraType = extras.getString(EXTRA_TYPE)
            when {
                extraItem != 0 && extraType.equals(TAG_UP_MOVIE) -> {
                    setupDataUpMovie(extraItem)
                }
                extraItem != 0 && extraType.equals(TAG_POP_MOVIE) -> {
                    setupDataPopMovie(extraItem)
                }
            }
        }
    }

    private fun setupDataUpMovie(extraItem: Int) {
        detailViewModel.setSelectedItem(extraItem)
        detailViewModel.getUpMovies.observe(this, { upMovie ->
            if (upMovie != null) {
                when (upMovie.status) {
                    Status.LOADING -> {
                        setupLoading()
                    }
                    Status.SUCCESS -> {
                        setupSuccess()
                        upMovie.data?.let { populateUpMovie(it) }
                        val state = upMovie.data?.bookmarked
                        if (state != null) {
                            setBookmarkState(state)
                        }
                    }
                    Status.ERROR -> {
                        setupError()
                    }
                }
            }
        })
    }

    private fun setupDataPopMovie(extraItem: Int) {
        detailViewModel.setSelectedItem(extraItem)
        detailViewModel.getPopMovies.observe(this, { popMovie ->
            if (popMovie != null) {
                when (popMovie.status) {
                    Status.LOADING -> {
                        setupLoading()
                    }
                    Status.SUCCESS -> {
                        setupSuccess()
                        popMovie.data?.let { populatePopMovie(it) }
                        val state = popMovie.data?.bookmarked
                        if (state != null) {
                            setBookmarkState(state)
                        }
                    }
                    Status.ERROR -> {
                        setupError()
                    }
                }
            }
        })
    }

    private fun setupLoading() {
        binding.run {
            progressBarDetail.visibility = View.VISIBLE
            tvDetailNotif.visibility = View.GONE
            hideContent()
        }
    }

    private fun setupSuccess() {
        binding.run {
            progressBarDetail.visibility = View.GONE
            showContent()
        }
    }

    private fun setupError() {
        binding.run {
            progressBarDetail.visibility = View.GONE
            hideContent()
            tvDetailNotif.visibility = View.VISIBLE
        }
    }

    private fun hideContent() {
        binding.run {
            imgDetailPoster.visibility = View.INVISIBLE
            tvDetailTitle.visibility = View.INVISIBLE
            tvDetailVoteCount.visibility = View.INVISIBLE
            tvDetailPopularity.visibility = View.INVISIBLE
            tvDetailOriginalLanguage.visibility = View.INVISIBLE
            cvDetailBookmark.visibility = View.INVISIBLE
            cvDetailShare.visibility = View.INVISIBLE
            tvDetailOverview.visibility = View.INVISIBLE
        }
    }

    private fun showContent() {
        binding.run {
            imgDetailPoster.visibility = View.VISIBLE
            tvDetailTitle.visibility = View.VISIBLE
            tvDetailVoteCount.visibility = View.VISIBLE
            tvDetailPopularity.visibility = View.VISIBLE
            tvDetailOriginalLanguage.visibility = View.VISIBLE
            cvDetailBookmark.visibility = View.VISIBLE
            cvDetailShare.visibility = View.VISIBLE
            tvDetailOverview.visibility = View.VISIBLE
        }
    }

    private fun populateUpMovie(upcomingMovieEntity: UpcomingMovieEntity) {
        binding.run {
            Glide.with(this@DetailActivity)
                .load("$IMAGE_BASE_URL${upcomingMovieEntity.posterPath}")
                .transform(RoundedCorners(30))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgDetailPoster)
            tvDetailTitle.text = upcomingMovieEntity.title
            tvDetailVoteCount.text =
                getString(R.string.vote_count, upcomingMovieEntity.voteCount.toString())
            tvDetailPopularity.text =
                getString(R.string.popularity, upcomingMovieEntity.popularity.toString())
            tvDetailOriginalLanguage.text =
                getString(R.string.original_language, upcomingMovieEntity.originalLanguage)
            tvDetailOverview.text = getString(R.string.overview, upcomingMovieEntity.overview)
            cvDetailBookmark.setOnClickListener {
                detailViewModel.setUpBookmarkMovie()
            }
            cvDetailShare.setOnClickListener {
                onUpMovieShareClick(upcomingMovieEntity)
            }
        }
    }

    private fun populatePopMovie(popularMovieEntity: PopularMovieEntity) {
        binding.run {
            Glide.with(this@DetailActivity)
                .load("$IMAGE_BASE_URL${popularMovieEntity.posterPath}")
                .transform(RoundedCorners(30))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error)
                )
                .into(imgDetailPoster)
            tvDetailTitle.text = popularMovieEntity.title
            tvDetailVoteCount.text =
                getString(R.string.vote_count, popularMovieEntity.voteCount.toString())
            tvDetailPopularity.text =
                getString(R.string.popularity, popularMovieEntity.popularity.toString())
            tvDetailOriginalLanguage.text =
                getString(R.string.original_language, popularMovieEntity.originalLanguage)
            tvDetailOverview.text = getString(R.string.overview, popularMovieEntity.overview)
            cvDetailBookmark.setOnClickListener {
                detailViewModel.setPopBookmarkMovie()
            }
            cvDetailShare.setOnClickListener {
                onPopMovieShareClick(popularMovieEntity)
            }
        }
    }

    private fun setBookmarkState(state: Boolean) {
        if (state) {
            binding.cvDetailBookmark.setImageResource(R.drawable.ic_baseline_bookmark_48)
        } else {
            binding.cvDetailBookmark.setImageResource(R.drawable.ic_outline_bookmark_border_48)
        }
    }

    override fun onUpMovieShareClick(upcomingMovieEntity: UpcomingMovieEntity) {
        val mimeTyep = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeTyep)
            .setChooserTitle(getString(R.string.share_up_title))
            .setText(getString(R.string.share_up_text, upcomingMovieEntity.title))
            .startChooser()
    }

    override fun onPopMovieShareClick(popularMovieEntity: PopularMovieEntity) {
        val mimeTyep = "text/plain"
        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeTyep)
            .setChooserTitle(getString(R.string.share_pop_title))
            .setText(getString(R.string.share_pop_text, popularMovieEntity.title))
            .startChooser()
    }
}