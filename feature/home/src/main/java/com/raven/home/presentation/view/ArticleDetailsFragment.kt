package com.raven.home.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.raven.home.R
import com.raven.home.databinding.FragmentArticleDetailsBinding
import com.raven.home.domain.models.Article
import com.raven.home.presentation.view.adapter.ImageSliderAdapter
import com.raven.home.utils.glide

class ArticleDetailsFragment(
    private var article : Article
) : Fragment() {
    private lateinit var mBinding : FragmentArticleDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentArticleDetailsBinding.inflate(layoutInflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setViewPager()
    }

    private fun initView() {
        with( mBinding) {
            ivTitle.glide(getString(R.string.logo_img))
            tvTitle.text = article.title
            tvAbstract.text = article.abstract
            tvDate.text = article.publishedDate
            tvByLine.text = article.byLine
            tvSection.text = article.section
            tvHyper.text = article.url
            ivBack.setOnClickListener {
                requireFragmentManager().popBackStack()
            }
        }
    }

    private fun setViewPager() {
        val listImg = mutableListOf<String>()
        if(article.media.isNotEmpty()) article.media[0].photos.forEach {
            listImg.add(it.url)
        } else listImg.add(getString(R.string.generic_img))

        val imgAdapter = ImageSliderAdapter(listImg)
        mBinding.vpImages.adapter = imgAdapter
    }


}