package eurofondas.news_task.retrofit

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Connection {
    var retro: Retrofit? = null

    private const val URL = "https://newsapi.org/";

    object NULL_TO_EMPTY_STRING_ADAPTER {
        @FromJson
        fun fromJson(reader: JsonReader): String {
            if (reader.peek() != JsonReader.Token.NULL) {
                return reader.nextString()
            }
            reader.nextNull<Unit>()
            return ""
        }
    }

    fun getRetrofit(): Retrofit? {

        if (retro == null) {
            val loggingInterceptor = HttpLoggingInterceptor()

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).add(NULL_TO_EMPTY_STRING_ADAPTER).build()

            retro = Retrofit.Builder().baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(
                    OkHttpClient().newBuilder().addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val originalHttpUrl = originalRequest.url

                        val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apiKey", "81f73061abb74a25a2794f43300039cd")
                            .build()

                        val requestBuilder = originalRequest.newBuilder().url(url)
                        val request = requestBuilder.build()

                        chain.proceed(request)
                    }.connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).build()
                )
                .build()
        }

        return retro
    }
}