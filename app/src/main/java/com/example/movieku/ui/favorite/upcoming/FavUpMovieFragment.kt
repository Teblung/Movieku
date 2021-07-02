package com.example.movieku.ui.favorite.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieku.R
import com.example.movieku.databinding.FragmentFavUpMovieBinding
import com.example.movieku.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavUpMovieFragment : Fragment() {

    private val binding: FragmentFavUpMovieBinding by lazy {
        FragmentFavUpMovieBinding.inflate(layoutInflater)
    }

    private lateinit var favUpMovieViewModel: FavUpMovieViewModel
    private lateinit var favUpMovieAdapter: FavUpMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavUpMovie)
    }

    override fun onResume() {
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            favUpMovieViewModel = ViewModelProvider(this, factory)[FavUpMovieViewModel::class.java]

            favUpMovieAdapter = FavUpMovieAdapter()
            binding.tvNotifFavoriteUpMovie.visibility = View.GONE
            binding.progressBarFavUpMovie.visibility = View.VISIBLE
            favUpMovieViewModel.getBookmarks().observe(viewLifecycleOwner, { movies ->
                if (movies.isNotEmpty()) {
                    binding.progressBarFavUpMovie.visibility = View.GONE
                    favUpMovieAdapter.submitList(movies)
                    favUpMovieAdapter.notifyDataSetChanged()
                } else {
                    binding.progressBarFavUpMovie.visibility = View.GONE
                    binding.tvNotifFavoriteUpMovie.visibility = View.VISIBLE
                }
            })
            binding.rvFavUpMovie.layoutManager = GridLayoutManager(context, 2)
            binding.rvFavUpMovie.setHasFixedSize(true)
            binding.rvFavUpMovie.adapter = favUpMovieAdapter
        }
        super.onResume()
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = favUpMovieAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { favUpMovieViewModel.setBookmark(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    binding.tvNotifFavoriteUpMovie.visibility = View.GONE
                    courseEntity?.let { favUpMovieViewModel.setBookmark(it) }
                }
                snackbar.show()
            }
        }
    })
}