package com.raven.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.raven.home.R
import com.raven.home.data.remote.models.Status
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.domain.models.Article
import com.raven.home.presentation.view.adapter.ArticlesAdapter
import com.raven.home.presentation.viewmodel.HomeViewModel
import com.raven.home.utils.glide
import com.raven.home.utils.isOnline
import com.raven.home.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var mBinding : HomeFragmentBinding
    private val viewModel : HomeViewModel by viewModels()

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
        if(isOnline(requireContext())) viewModel.getNews()
        else requireContext().toast("without Internet")

        mBinding.ivTitle.glide(getString(R.string.logo_img))
        livedata()
        flowStates()
    }

    private fun livedata() {
        viewModel
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
                        Status.ERROR -> Unit
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
            listArticles = listOf(Article(),Article(),Article(),Article(),Article(),
                Article(),Article(),Article(),Article(),Article()),
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
        val fragmentManager: FragmentManager? = fragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.cl_main, ArticleDetailsFragment(article))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.commit()
        mBinding.articlesAdapter.apply {
            isClickable = false
            isFocusable = false
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.articlesAdapter.apply {
            isClickable = true
            isFocusable = true
        }
    }
}
