package com.dev.inukai_masanori.myskill.UI

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.dev.inukai_masanori.myskill.Data.Article


class ArticleListAdapter (/* プライマリーコンストラクタ */ private val context: Context) : BaseAdapter() {

    /* 空のリストを作成 */
    var articles: List<Article> = emptyList()

    /*
        【optional】
        Swiftとほぼ同じ。?がついているのもは、nullを返す可能性があるので、
        使用する前に必ずnullじゃないことを確認しなければならない。
     */
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
        /*
            【スコープ関数 apply】
            自分自身に大して複数の処理をしたい時に使用する。
            自分自身が戻り値になる。

            【?:】
            Swift でいう ?? に該当。前の処理がnullの時に実行される。
        */
        return ((convertView as? ArticleView) ?: ArticleView(context)).apply {
            setArticle(articles[position])
        }
    }
}