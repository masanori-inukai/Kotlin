package com.dev.inukai_masanori.myskill.UI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.dev.inukai_masanori.myskill.Data.Article
import com.dev.inukai_masanori.myskill.R

/*
    【記事詳細】
    リストから選択後のページ
 */
class ArticleActivity : AppCompatActivity() {

    /*
        シングルトン
        Staticフィールド
     */
    companion object {

        private const val ARTICLE_EXTRA: String = "article"

        fun intent(context: Context, article: Article): Intent {
            return Intent(context, ArticleActivity::class.java).putExtra(ARTICLE_EXTRA, article)
        }
    }

    /*
        【by lazy】(Property Delegation)
        遅延実行のしくみ。このプロパティに始めてアクセスされた時にブロックが実行される。
     */
    private val articleView: ArticleView by lazy { findViewById(R.id.article_view) as ArticleView }
    private val webView: WebView by lazy { findViewById(R.id.web_view) as WebView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val article: Article = intent.getParcelableExtra(ARTICLE_EXTRA)
        articleView.setArticle(article)
        webView.loadUrl(article.url)
    }
}