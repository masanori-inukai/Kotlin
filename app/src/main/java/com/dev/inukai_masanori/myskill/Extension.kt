@file:Suppress("UNCHECKED_CAST")

package com.dev.inukai_masanori.myskill

import android.support.annotation.IdRes
import android.view.View

/**
* Created by inukai_masanori on 2017/06/26.
*/

fun <T: View> View.bindView(@IdRes id: Int): Lazy<T> {
    return lazy {
        findViewById(id) as T
    }
}