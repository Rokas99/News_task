package eurofondas.news_task.activities

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import eurofondas.news_task.R
import eurofondas.news_task.logic.Logic
import eurofondas.news_task.models.Article
import eurofondas.news_task.viewmodels.NewsViewModel

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val newsViewModel : NewsViewModel by viewModels()

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
        val save: ImageView = findViewById(R.id.activity_details_save)
        val read_online_button: Button = findViewById(R.id.activity_details_read_online)

        back.setOnClickListener() {
            finish()
        }

        save.setOnClickListener() {
            if (article != null) {
                newsViewModel.insertArticle(article)

                Toast.makeText(this, "Article was successfully added", Toast.LENGTH_SHORT).show()
            }
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

        newsViewModel.getErrorsResult().observe(this) {
            if (it != null) {
                Toasty.error(this, it, Toast.LENGTH_LONG, true).show()
            }
        }
    }

    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }
}