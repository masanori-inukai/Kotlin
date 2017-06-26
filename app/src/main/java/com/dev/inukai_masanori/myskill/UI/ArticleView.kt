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
import com.dev.inukai_masanori.myskill.bindView

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

    val profileImageView: ImageView by bindView(R.id.profile_image_view)
    val titleTextView: TextView by bindView(R.id.title_text_view)
    val userNameTextView: TextView by bindView(R.id.user_name_text_view)

    init {
        LayoutInflater.from(this.context).inflate(R.layout.list_article, this)
    }

    fun setArticle(article: Article) {
        titleTextView.text = article.title
        userNameTextView.text = article.user.name
        profileImageView.setBackgroundColor(Color.RED)
    }
}