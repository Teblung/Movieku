package com.example.movieku.ui.home.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieku.R
import com.example.movieku.data.source.local.entity.PopularMovieEntity
import com.example.movieku.databinding.FragmentPopMovieBinding
import com.example.movieku.viewmodel.ViewModelFactory
import com.example.movieku.vo.Resource
import com.example.movieku.vo.Status

class PopMovieFragment : Fragment() {

    private val binding: FragmentPopMovieBinding by lazy {
        FragmentPopMovieBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[PopMovieViewModel::class.java]

            val popMovieAdapter = PopMovieAdapter()

            viewModel.getPopcomingMovies().observe(viewLifecycleOwner, { popMovies ->
                if (popMovies != null) {
                    when (popMovies.status) {
                        Status.LOADING -> {
                            setupLoading()
                        }
                        Status.SUCCESS -> {
                            setupSuccess(popMovieAdapter, popMovies)
                        }
                        Status.ERROR -> {
                            setupError()
                        }
                    }
                }
            })

            with(binding.rvPopMovie) {
                this.layoutManager = GridLayoutManager(context, 2)
                this.setHasFixedSize(true)
                this.adapter = popMovieAdapter
            }
        }
    }

    private fun setupLoading() {
        binding.progressBarPopMovie.visibility = View.VISIBLE
    }

    private fun setupSuccess(
        popMovieAdapter: PopMovieAdapter,
        popMovies: Resource<PagedList<PopularMovieEntity>>
    ) {
        binding.progressBarPopMovie.visibility = View.GONE
        popMovieAdapter.submitList(popMovies.data)
        popMovieAdapter.notifyDataSetChanged()
    }

    private fun setupError() {
        binding.progressBarPopMovie.visibility = View.GONE
        Toast.makeText(
            context,
            getString(R.string.there_is_an_error),
            Toast.LENGTH_SHORT
        ).show()
    }
}