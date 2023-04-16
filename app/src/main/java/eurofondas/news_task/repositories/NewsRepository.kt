package eurofondas.news_task.repositories

import eurofondas.news_task.models.GetNewsResponse
import eurofondas.news_task.retrofit.Connection
import eurofondas.news_task.retrofit.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    fun getNews(newsResponse : INewsResponse)
    {
        val newsService : NewsService? = Connection.getRetrofit()?.create(NewsService::class.java)

        newsService?.getNews()?.enqueue(object : Callback<GetNewsResponse> {
            override fun onResponse(
                call: Call<GetNewsResponse>,
                response: Response<GetNewsResponse>
            ) {
                if(response.isSuccessful)
                {
                    newsResponse.OnResponse(response.body())
                }
                else
                {
                    newsResponse.OnFailure(Throwable(response.message()))
                }
            }

            override fun onFailure(call: Call<GetNewsResponse>, t: Throwable) {
                newsResponse.OnFailure(t)
            }
        })

    }

    interface INewsResponse
    {
        fun OnResponse(getNewsResponse : GetNewsResponse?);
        fun OnFailure(t: Throwable);
    }
}