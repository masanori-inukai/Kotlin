package com.dev.inukai_masanori.myskill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dev.inukai_masanori.myskill.Data.Article
import com.dev.inukai_masanori.myskill.Data.User
import com.dev.inukai_masanori.myskill.UI.ArticleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleView = ArticleView(applicationContext)
        articleView.setArticle(
                Article(
                        id = "111",
                        title = "kotlin",
                        url = "http:/test.com",
                        user = User(id = "222", name = "inukai", profileImageUrl = "")
                )
        )

        setContentView(articleView)
    }
}
