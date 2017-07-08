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

/*
    アプリ起動時に呼ばれるActivity
    RxLifecycleを使用する際には、AppCompatActivityではなく、RxAppCompatActivityを継承する必要がある。
 */
class MainActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 検索結果のJSONをKotlinのデータクラスと自動で紐つけてくれる。
        val gson = GsonBuilder()
                // 紐つける際の命名規則を指定
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

        // 通信を行うライブラリの初期化
        val retrofit = Retrofit.Builder()
                .baseUrl("http://qiita.com")
                // このベースURLを基準にArticleClientのアノテーションでURLが作成される。
                .addConverterFactory(GsonConverterFactory.create(gson))
                // gsonと紐つける
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        val articleClient = retrofit.create(ArticleClient::class.java)

        val listView = findViewById(R.id.list_view) as ListView
        val queryEditText = findViewById(R.id.query_edit_text) as EditText
        val searchButton = findViewById(R.id.search_button) as Button
        val progressBar = findViewById(R.id.progress_bar) as ProgressBar

        val listAdapter = ArticleListAdapter(applicationContext)
//        listAdapter.articles = listOf(mockObject(), mockObject())
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            /*
                【ラベル】
                引数にラベルを設定することができ、ラベルの指定をすれば、引数の順序は関係なくなる
                関数のオーバーロードも容易
             */
            ArticleActivity.intent(context = this, article = article).let {
                startActivity(it)
            }
        }

        searchButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            /*
                【RxAndroid】
                ここでは、非同期で読み込み、結果が返ってきたらUIスレッドでsubscribe内のブロックを処理させている。

                【RxLifecycle】
                bindToLifecycleによって、ライフサイクルの通知を受け取り、アクティビティが破棄された時に発生しがちな、
                エラーやメモリーリークを防いでいる。
                Swift だと Weak Self 的なことかな。
             */
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

    private fun mockObject(): Article {
        return Article(
                id = "111",
                title = "kotlin",
                url = "http:/test.com",
                user = User(id = "222", name = "inukai", profileImageUrl = "")
        )
    }
}
