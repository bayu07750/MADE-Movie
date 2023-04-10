package com.bayu07750.mademovie.bookmark.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bayu07750.mademovie.bookmark.databinding.FragmentBookmarkBinding
import com.bayu07750.mademovie.bookmark.di.BookmarkModule
import com.bayu07750.mademovie.presentation.base.BaseFragment
import com.bayu07750.mademovie.MainGraphDirections
import com.bayu07750.mademovie.presentation.adapter.MovieGridAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class BookmarkFragment :
    BaseFragment<FragmentBookmarkBinding>(com.bayu07750.mademovie.bookmark.R.layout.fragment_bookmark) {

    private val viewModel: BookmarkViewModel by viewModel()

    private lateinit var bookmarkAdapter: MovieGridAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(BookmarkModule)

        initView()
        observe()
    }

    override fun initView() {
        super.initView()

        bookmarkAdapter = MovieGridAdapter { _, movie ->
            findNavController().navigate(MainGraphDirections.actionGlobalDetailGraph(movie))
        }
        binding.rvBookmarkedMovie.adapter = bookmarkAdapter
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.bookmarkedMovies.collect { result ->
                        binding.tvEmpty.isVisible = result.isEmpty()
                        bookmarkAdapter.submitList(result)
                    }
                }
            }
        }
    }
}