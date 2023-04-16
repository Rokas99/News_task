package eurofondas.news_task.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    @ColumnInfo(name = "source_id")
    val id: String,
    @ColumnInfo(name = "source_name")
    val name: String
) : Parcelable