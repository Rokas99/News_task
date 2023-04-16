package eurofondas.news_task.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.load
import eurofondas.news_task.R
import eurofondas.news_task.activities.DetailsActivity
import eurofondas.news_task.logic.Logic
import eurofondas.news_task.models.Article
import eurofondas.news_task.viewmodels.NewsViewModel


class NewsAdapter(private val items: List<Article>, private val activity: Activity,  private val clickable: Boolean ) :
    RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_article, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title

        holder.date.text = Logic().formatDate(item.publishedAt)

        if (item.urlToImage != "")
            holder.image.load(item.urlToImage)
        else {
            holder.image.load(R.drawable.news)
        }

        if(clickable)
        {
            holder.element.setOnClickListener() {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra("article", item)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.single_article_title)
    val date: TextView = itemView.findViewById(R.id.single_article_date)
    val image: ImageView = itemView.findViewById(R.id.single_article_image)
    val element: ConstraintLayout = itemView.findViewById(R.id.single_article_element)
}
