package com.raven.home.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.raven.home.R
import com.raven.home.databinding.FragmentArticleDetailsBinding
import com.raven.home.domain.models.Article
import com.raven.home.presentation.viewmodel.HomeViewModel
import com.raven.home.utils.glide
import com.raven.home.utils.isOnline
import com.raven.home.utils.short
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {
    private lateinit var mBinding : FragmentArticleDetailsBinding
    private lateinit var article : Article
    private val viewModel : HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentArticleDetailsBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = viewModel.getArticle()
        initView()
    }

    private fun initView() {
        with( mBinding) {
            if(isOnline(requireContext())) mBinding.ivTitle.glide(getString(R.string.logo_img))
            if(article.media.isNotEmpty() && isOnline(requireContext())) mBinding.clViewP.glide(
                //if the array is not empty, give 3 images and the last has the maximum resolution
                article.media[0].photos[2].url
            ) else mBinding.clViewP.glide(getString(R.string.generic_img))
            tvTitle.text = article.title
            tvAbstract.text = article.abstract
            tvDate.text = article.publishedDate
            tvByLine.text = article.byLine.short(16)
            tvSection.text = article.section
            tvHyper.text = article.url
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvHyper.setOnClickListener {
                val url = article.url
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }
}