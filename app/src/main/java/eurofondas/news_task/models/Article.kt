package eurofondas.news_task.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    @Embedded val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : Parcelable