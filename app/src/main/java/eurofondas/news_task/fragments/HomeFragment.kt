package eurofondas.news_task.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eurofondas.news_task.R
import eurofondas.news_task.activities.MainActivity
import eurofondas.news_task.adapters.NewsAdapter
import eurofondas.news_task.db.ArticleDatabase
import eurofondas.news_task.decoration.SpaceItemDecoration
import eurofondas.news_task.viewmodels.ArticleViewModelFactory
import eurofondas.news_task.viewmodels.NewsViewModel

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainActivity = activity as MainActivity

        val recyclerView : RecyclerView = mainActivity.findViewById(R.id.fragment_home_recyclerView)

        val linearLayoutManager = LinearLayoutManager(mainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(SpaceItemDecoration(30, 10))

        val articleDatabase = ArticleDatabase.getInstance(mainActivity)
        val viewModelFactory = ArticleViewModelFactory(articleDatabase)
        val newsViewModel = ViewModelProvider(mainActivity, viewModelFactory)[NewsViewModel::class.java]

        newsViewModel.getNews()

        newsViewModel.getNewsResult().observe(mainActivity
        ) {
            if(it != null)
            {
                val articles = it.articles
                val adapter = NewsAdapter(articles, mainActivity, true)
                recyclerView.adapter = adapter
            }
        }
    }

}