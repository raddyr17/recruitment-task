package com.raddyr.recruitmenttask.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raddyr.recruitmenttask.R
import com.raddyr.recruitmenttask.databinding.FragmentArticleListLayoutBinding
import com.raddyr.recruitmenttask.util.DataState
import com.raddyr.recruitmenttask.util.extensions.hide
import com.raddyr.recruitmenttask.util.extensions.show
import com.raddyr.recruitmenttask.util.extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : Fragment(R.layout.fragment_article_list_layout) {

    private var _binding: FragmentArticleListLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleListViewModel by viewModels()
    private val articleAdapter by lazy { ArticleAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleListLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        subscribeObservers()
        setPullToRefresh()
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.dataState.collect {
                when (it) {
                    is DataState.Loading -> {
                        binding.pbLoader.show()
                    }
                    is DataState.Success -> {
                        binding.tvNoDataLabel.hide()
                        showContent(it.data)
                    }
                    is DataState.Error -> {
                        binding.tvNoDataLabel.show()
                        binding.root.showSnackbar(getString(R.string.error_label))
                    }

                    is DataState.NoInternet -> {
                        showContent(it.data)
                        binding.root.showSnackbar(getString(R.string.error_label))
                    }
                }
                if (it != DataState.Loading) {
                    with(binding) {
                        pbLoader.hide()
                    }
                }
            }
        }
    }

    private fun showContent(list: List<Article>?) {
        if (list.isNullOrEmpty()) {
            showEmptyListLabel()
        } else {
            articleAdapter.submitList(list)
        }
    }

    private fun showEmptyListLabel() {
        with(binding) {
            tvNoDataLabel.show()
            rvList.hide()
        }
    }

    private fun setAdapter() {
        with(binding.rvList) {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setPullToRefresh() {
        with(binding.srSwipeRefresh) {
            setOnRefreshListener {
                isRefreshing = true
                viewModel.setEvent(ListEvent.GeArticles)
                isRefreshing = false
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}