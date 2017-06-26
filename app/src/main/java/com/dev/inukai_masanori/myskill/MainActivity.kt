package com.dev.inukai_masanori.myskill

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.dev.inukai_masanori.myskill.Data.Article
import com.dev.inukai_masanori.myskill.Data.User
import com.dev.inukai_masanori.myskill.UI.ArticleActivity
import com.dev.inukai_masanori.myskill.UI.ArticleListAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext);
        listAdapter.articles = listOf(dummy(), dummy())
        val listView = findViewById(R.id.list_view) as ListView
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(context = this, article = article).let {
                startActivity(it)
            }
        }
    }

    private fun dummy(): Article {
        return Article(
                id = "111",
                title = "kotlin",
                url = "http:/test.com",
                user = User(id = "222", name = "inukai", profileImageUrl = "")
        )
    }
}
