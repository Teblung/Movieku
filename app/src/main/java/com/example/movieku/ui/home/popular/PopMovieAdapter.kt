package com.example.movieku.ui.home.popular

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieku.R
import com.example.movieku.constant.AppConstant
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_ITEM
import com.example.movieku.constant.AppConstant.INTENT.EXTRA_TYPE
import com.example.movieku.constant.AppConstant.INTENT.TAG_POP_MOVIE
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.databinding.ItemsMovieBinding
import com.example.movieku.ui.detail.DetailActivity

class PopMovieAdapter :
    PagedListAdapter<PopularMovieEntity, PopMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMovieEntity>() {
            override fun areItemsTheSame(
                oldItem: PopularMovieEntity,
                newItem: PopularMovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PopularMovieEntity,
                newItem: PopularMovieEntity
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
        fun bind(movie: PopularMovieEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load("${AppConstant.INTENT.IMAGE_BASE_URL}${movie.posterPath}")
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
                            putExtra(EXTRA_TYPE, TAG_POP_MOVIE)
                        })
                }
            }
        }
    }
}