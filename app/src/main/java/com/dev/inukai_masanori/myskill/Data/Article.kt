package com.dev.inukai_masanori.myskill.Data

import android.os.Parcel
import android.os.Parcelable

/*
    【data class】
    これだけでコンパイラが勝手に以下のものを作ってくれる。ただしクラス内または継承元に明示的に定義されていれば勝手に生成したりしない。
    何もしないけどデータだけ保持したいクラスってよく作るよね？そういうのには"dataって付けよう。

    ・equals()/hashCode()ペア
    ・"User(name=John, age=42)"って表示するtoString()
    ・宣言順で内容を取り出すcomponentN()関数
    ・copy()


    【Parcelable】
    Activityから別のActivityに自分で作ったクラスのオブジェクトを渡したりするとき、そのクラスにParcelableを実装する必要がある。
 */
data class Article(val id: String,
                   val title: String,
                   val url: String,
                   val user: User) : Parcelable {

    /*
        【object宣言】
        これをつけるだけで、シングルトンになる。

        【companion】
        シングルトンをクラス内に作成する場合は、companionをつける必要がある。
        クラス内に一つだけ。

     */
    companion object {
        /*
            【@JvmField】
            Javaからアクセスするには、User.Companion.getCREATOR()のようにCompanionというオブジェクトと
            そのメソッドを使用しなければならない。しかし、このアノテーションをつけることで、
            staticフィールドとしてUser.CREATORのようにアクセスすることができる。
            Gsonでのやり取りに必要。
         */
        @JvmField
        val CREATOR: Parcelable.Creator<Article> = object : Parcelable.Creator<Article> {

            /*
                【スコープ関数 run】
                自分自身に大して複数の処理をしたい時に使用する。
                ブロックの最後の実行結果が返り値となる。
            */
            override fun createFromParcel(source: Parcel): Article = source.run {
                Article(readString(), readString(), readString(), readParcelable(User::class.java.classLoader))
            }

            override fun newArray(size: Int): Array<Article?> = arrayOfNulls(size)
        }
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        /*
            【スコープ関数 run】
            自分自身に大して複数の処理をしたい時に使用する。
            ブロックの最後の実行結果が返り値となる。
         */
        dest.run {
            /* dest. */ writeString(id)
            /* dest. */ writeString(title)
            /* dest. */ writeString(url)
            /* dest. */ writeParcelable(user, flags)
        }
    }
}