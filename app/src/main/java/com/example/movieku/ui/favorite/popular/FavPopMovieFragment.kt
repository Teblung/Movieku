package com.example.movieku.ui.favorite.popular

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
import com.example.movieku.databinding.FragmentFavPopMovieBinding
import com.example.movieku.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavPopMovieFragment : Fragment() {

    private val binding: FragmentFavPopMovieBinding by lazy {
        FragmentFavPopMovieBinding.inflate(layoutInflater)
    }

    private lateinit var favPopMovieViewModel: FavPopMovieViewModel
    private lateinit var favPopMovieAdapter: FavPopMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvFavPopMovie)
    }

    override fun onResume() {
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            favPopMovieViewModel =
                ViewModelProvider(this, factory)[FavPopMovieViewModel::class.java]

            favPopMovieAdapter = FavPopMovieAdapter()
            binding.tvNotifFavoritePopMovie.visibility = View.GONE
            binding.progressBarFavPopMovie.visibility = View.VISIBLE
            favPopMovieViewModel.getBookmarks().observe(viewLifecycleOwner, { movies ->
                if (movies.isNotEmpty()) {
                    binding.progressBarFavPopMovie.visibility = View.GONE
                    favPopMovieAdapter.submitList(movies)
                    favPopMovieAdapter.notifyDataSetChanged()
                } else {
                    binding.progressBarFavPopMovie.visibility = View.GONE
                    binding.tvNotifFavoritePopMovie.visibility = View.VISIBLE
                }
            })
            binding.rvFavPopMovie.layoutManager = GridLayoutManager(context, 2)
            binding.rvFavPopMovie.setHasFixedSize(true)
            binding.rvFavPopMovie.adapter = favPopMovieAdapter
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
                val courseEntity = favPopMovieAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { favPopMovieViewModel.setBookmark(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    binding.tvNotifFavoritePopMovie.visibility = View.GONE
                    courseEntity?.let { favPopMovieViewModel.setBookmark(it) }
                }
                snackbar.show()
            }
        }
    })
}