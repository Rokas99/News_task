package eurofondas.news_task.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.InvalidationTracker
import eurofondas.news_task.R
import eurofondas.news_task.adapters.NewsAdapter
import eurofondas.news_task.decoration.SpaceItemDecoration
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.retrofit.Connection
import eurofondas.news_task.retrofit.NewsService
import eurofondas.news_task.viewmodels.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivity = this

        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.activity_main_recyclerView)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(SpaceItemDecoration(30, 10))

        val newsViewModel = NewsViewModel()

        newsViewModel.getNews()

        newsViewModel.getNewsResult().observe(this
        ) {
            if(it != null)
            {
                val articles = it.articles
                val adapter = NewsAdapter(articles, mainActivity)
                recyclerView.adapter = adapter
            }
        }
    }
}