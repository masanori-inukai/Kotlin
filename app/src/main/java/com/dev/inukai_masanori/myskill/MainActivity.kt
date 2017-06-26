package com.dev.inukai_masanori.myskill

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.dev.inukai_masanori.myskill.Data.Article
import com.dev.inukai_masanori.myskill.Data.User
import com.dev.inukai_masanori.myskill.UI.ArticleActivity
import com.dev.inukai_masanori.myskill.UI.ArticleClient
import com.dev.inukai_masanori.myskill.UI.ArticleListAdapter
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://qiita.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        val articleClient = retrofit.create(ArticleClient::class.java)

        val listView = findViewById(R.id.list_view) as ListView
        val queryEditText = findViewById(R.id.query_edit_text) as EditText
        val searchButton = findViewById(R.id.search_button) as Button
        val progressBar = findViewById(R.id.progress_bar) as ProgressBar


        val listAdapter = ArticleListAdapter(applicationContext)
//        listAdapter.articles = listOf(dummy(), dummy())
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(context = this, article = article).let {
                startActivity(it)
            }
        }

        searchButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            articleClient.search(queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate {
                        progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe({
                        queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                        toast("error $it")
                    })
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
