package com.dev.inukai_masanori.myskill.UI

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dev.inukai_masanori.myskill.Data.Article

/**
 * Created by inukai_masanori on 2017/06/26.
 */
class ArticleListAdapter (private val context: Context) : BaseAdapter() {

    var articles: List<Article> = emptyList()

    override fun getItem(position: Int): Any? {
        return articles[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return articles.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return ((convertView as? ArticleView) ?: ArticleView(context)).apply {
            setArticle(articles[position])
        }
    }
}