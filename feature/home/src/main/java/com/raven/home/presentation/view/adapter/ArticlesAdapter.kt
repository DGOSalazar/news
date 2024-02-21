package com.raven.home.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raven.home.R
import com.raven.home.databinding.ItemArticleBinding
import com.raven.home.domain.models.Article
import com.raven.home.utils.glide
import com.raven.home.utils.short

class ArticlesAdapter(
    private var listArticles: List<Article> = listOf(),
    private var onClick: (Article) -> Unit,
    private val context: Context
): RecyclerView.Adapter<ArticlesAdapter.AdapterViewHolder>() {

    inner class AdapterViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val mBinding = ItemArticleBinding.bind(view)

        fun initView(article: List<Article>, click: (Article) -> Unit, n: Int) {
            if(article[0].title.isEmpty()) return
            val img =
                if (article[n].media.isEmpty() || article[n].media[0].photos.isEmpty())
                    context.getString(R.string.generic_img)
                else article[n].media[0].photos[0].url
            mBinding.apply {
                tvTitle.text = article[n].title.short(16)
                tvAbstract.text = article[n].abstract.short(55)
                ivArticle.glide(img)
                tvByLine.text = article[n].byLine.short(20)
                root.setOnClickListener {
                    click(article[n])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        return AdapterViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent,false))
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.initView(
            article = listArticles,
            click = {
                onClick(it)
            },
            n = position
        )
    }

    override fun getItemCount(): Int = listArticles.size
}