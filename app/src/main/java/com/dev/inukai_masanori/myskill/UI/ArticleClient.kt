package com.dev.inukai_masanori.myskill.UI

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import com.dev.inukai_masanori.myskill.Data.Article

interface ArticleClient {

    /*
        【retrofit】
        作法的な書き方。
     */
    @GET("/api/v2/items")
    fun search(@Query("query") query: String): Observable<List<Article>>
}