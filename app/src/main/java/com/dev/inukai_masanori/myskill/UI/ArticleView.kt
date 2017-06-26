package com.dev.inukai_masanori.myskill.UI

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.dev.inukai_masanori.myskill.Data.Article
import com.dev.inukai_masanori.myskill.R

/**
 * Created by inukai_masanori on 2017/06/26.
 */

class ArticleView : FrameLayout {

    constructor(context: Context?) :
            super(context)

    constructor(context: Context?, attr: AttributeSet?) :
            super(context, attr)

    constructor(context: Context?, attr: AttributeSet?, defStyleAttr: Int) :
            super(context, attr, defStyleAttr)

    constructor(context: Context?, attr: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attr, defStyleAttr, defStyleRes)

    var profileImageView: ImageView? = null
    var titleTextView: TextView? = null
    var userNameTextView: TextView? = null

    init {
        LayoutInflater.from(this.context).inflate(R.layout.list_article, this)
        this.profileImageView = findViewById(R.id.profile_image_view) as ImageView
        this.titleTextView = findViewById(R.id.title_text_view) as TextView
        this.userNameTextView = findViewById(R.id.user_name_text_view) as TextView
    }

    fun setArticle(article: Article) {
        titleTextView?.text = article.title
        userNameTextView?.text = article.user.name
        profileImageView?.setBackgroundColor(Color.RED)
    }
}