package com.example.movieku.ui.home.upcoming

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieku.R
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_ITEM
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_TYPE
import com.example.movieku.constant.AppConstant.INTENT.IMAGE_BASE_URL
import com.example.movieku.constant.AppConstant.INTENT.TAG_UP_MOVIE
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.databinding.ItemsMovieBinding
import com.example.movieku.ui.detail.DetailActivity

class UpMovieAdapter :
    PagedListAdapter<UpcomingMovieEntity, UpMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UpcomingMovieEntity>() {
            override fun areItemsTheSame(
                oldItem: UpcomingMovieEntity,
                newItem: UpcomingMovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UpcomingMovieEntity,
                newItem: UpcomingMovieEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    class MovieViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: UpcomingMovieEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("$IMAGE_BASE_URL${movie.posterPath}")
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgMovie)
                tvTitleMovie.text = movie.title
                itemView.setOnClickListener {
                    itemView.context.startActivity(
                        Intent(
                            itemView.context,
                            DetailActivity::class.java
                        ).apply {
                            putExtra(EXTRA_ITEM, movie.id)
                            putExtra(EXTRA_TYPE, TAG_UP_MOVIE)
                        })
                }
            }
        }
    }
}