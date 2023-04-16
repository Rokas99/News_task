package eurofondas.news_task.activities

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import eurofondas.news_task.R
import eurofondas.news_task.logic.Logic
import eurofondas.news_task.models.Article


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent = this.intent
        val bundle = intent.extras

        val article : Article? = bundle?.parcelable("article")

        val titleView: TextView = findViewById(R.id.activity_details_title)
        val dateView: TextView = findViewById(R.id.activity_details_date)
        val contentView: TextView = findViewById(R.id.activity_details_content)
        val imageView: ImageView = findViewById(R.id.activity_details_image)
        val back: ImageView = findViewById(R.id.activity_details_back)
        val read_online_button: Button = findViewById(R.id.activity_details_read_online)

        back.setOnClickListener() {
            finish()
        }

        if (article != null) {
            titleView.text = article.title

            dateView.text = Logic().formatDate(article.publishedAt)

            contentView.text = article.content

            imageView.load(article.urlToImage)

            read_online_button.setOnClickListener() {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(intent)
            }
        }

    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}