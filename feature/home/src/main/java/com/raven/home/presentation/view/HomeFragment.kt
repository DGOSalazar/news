package com.raven.home.presentation.view

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.raven.home.R
import com.raven.home.utils.Status
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.domain.models.Article
import com.raven.home.presentation.view.adapter.ArticlesAdapter
import com.raven.home.presentation.viewmodel.HomeViewModel
import com.raven.home.utils.NetworkMonitor
import com.raven.home.utils.glide
import com.raven.home.utils.isOnline
import com.raven.home.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mBinding : HomeFragmentBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private lateinit var networkMonitor : NetworkMonitor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = HomeFragmentBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNews()
        networkMonitor = NetworkMonitor(requireContext())
        networkMonitor.registerNetworkCallback(networkCallback)

        if(isOnline(requireContext())) mBinding.ivTitle.glide(getString(R.string.logo_img))
        flowStates()
    }
    private fun flowStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsState.collect { news ->
                    when(news.status) {
                        Status.LOADING -> initShimmerAdapter()
                        Status.SUCCESS -> {
                            mBinding.apply {
                                shimmer.hideShimmer()
                                shimmer.isVisible = false
                                articlesAdapter.isVisible = true
                            }
                            initAdapter(news.data!!)
                        }
                        Status.ERROR -> {
                            initShimmerAdapter()
                            requireContext().toast(
                                "${news.code.toString()} ${news.message.toString()}"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter(list: List<Article>) {
        val mAdapter = ArticlesAdapter(
            listArticles = list,
            context = requireContext(),
            onClick = {
                launchFragment(it)
            }
        )
        mBinding.articlesAdapter.apply {
            layoutManager = GridLayoutManager(
                mBinding.articlesAdapter.context,
                2
            )
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initShimmerAdapter() {
        val mockAdapter = ArticlesAdapter(
            listArticles =
            listOf(
                Article(),Article(),Article(),Article(),Article(),
                Article(),Article(),Article(),Article(),Article()
            ),
            context = requireContext(),
            onClick = {}
        )
        mBinding.articlesAdapterShimmer.apply {
            layoutManager = GridLayoutManager(
                mBinding.articlesAdapter.context,
                2
            )
            setHasFixedSize(true)
            adapter = mockAdapter
        }
    }

    private fun launchFragment(article: Article) {
        viewModel.saveArticle(article)
        val request = NavDeepLinkRequest.Builder
            .fromUri(getString(R.string.deep_link_fragment_detailed).toUri())
            .build()
        networkMonitor.unregisterNetworkCallback(networkCallback)
        findNavController().navigate(request)
    }

    override fun onResume() {
        super.onResume()
        mBinding.articlesAdapter.apply {
            isClickable = true
            isFocusable = true
        }
    }

    //network Monitor
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            viewModel.getNews()
            requireContext().toast(getString(R.string.connection_restored))
        }
        override fun onLost(network: Network) {
            viewModel.getNews()
            requireContext().toast(getString(R.string.connection_lost))
        }
    }
}
