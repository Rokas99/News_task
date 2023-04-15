package eurofondas.news_task.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eurofondas.news_task.R
import eurofondas.news_task.adapters.NewsAdapter
import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.retrofit.Connection
import eurofondas.news_task.retrofit.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.activity_main_recyclerView)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        val newsService : NewsService? = Connection.getRetrofit()?.create(NewsService::class.java)

        newsService?.getNews()?.enqueue(object : Callback<GetNewsResponse> {
            override fun onResponse(
                call: Call<GetNewsResponse>,
                response: Response<GetNewsResponse>
            ) {
                if(response.isSuccessful)
                {
                    if(response.body() != null)
                    {
                        val articles = response.body()!!.articles
                        val adapter = NewsAdapter(articles)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<GetNewsResponse>, t: Throwable) {
                Log.d("FAILURE", t.toString())
            }
        })
    }
}