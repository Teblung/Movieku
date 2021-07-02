package com.example.movieku.ui.home.upcoming

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
import com.example.movieku.data.source.local.entity.UpcomingMovieEntity
import com.example.movieku.databinding.FragmentUpMovieBinding
import com.example.movieku.viewmodel.ViewModelFactory
import com.example.movieku.vo.Resource
import com.example.movieku.vo.Status


class UpMovieFragment : Fragment() {

    private val binding: FragmentUpMovieBinding by lazy {
        FragmentUpMovieBinding.inflate(layoutInflater)
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
            val viewModel = ViewModelProvider(this, factory)[UpMovieViewModel::class.java]

            val upMovieAdapter = UpMovieAdapter()

            viewModel.getUpcomingMovies().observe(viewLifecycleOwner, { upMovies ->
                if (upMovies != null) {
                    when (upMovies.status) {
                        Status.LOADING -> {
                            setupLoading()
                        }
                        Status.SUCCESS -> {
                            setupSuccess(upMovieAdapter, upMovies)
                        }
                        Status.ERROR -> {
                            setupError()
                        }
                    }
                }
            })

            with(binding.rvUpMovie) {
                this.layoutManager = GridLayoutManager(context, 2)
                this.setHasFixedSize(true)
                this.adapter = upMovieAdapter
            }
        }
    }

    private fun setupLoading() {
        binding.progressBarUpMovie.visibility = View.VISIBLE
    }

    private fun setupSuccess(
        upMovieAdapter: UpMovieAdapter,
        upMovies: Resource<PagedList<UpcomingMovieEntity>>
    ) {
        binding.progressBarUpMovie.visibility = View.GONE
        upMovieAdapter.submitList(upMovies.data)
        upMovieAdapter.notifyDataSetChanged()
    }

    private fun setupError() {
        binding.progressBarUpMovie.visibility = View.GONE
        Toast.makeText(
            context,
            getString(R.string.there_is_an_error),
            Toast.LENGTH_SHORT
        ).show()
    }
}