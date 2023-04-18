package eurofondas.news_task.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import eurofondas.news_task.R
import eurofondas.news_task.activities.MainActivity
import eurofondas.news_task.adapters.NewsAdapter
import eurofondas.news_task.decoration.SpaceItemDecoration
import eurofondas.news_task.viewmodels.NewsViewModel

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mainActivity = activity as MainActivity

        val recyclerView : RecyclerView = mainActivity.findViewById(R.id.fragment_saved_recyclerView)

        val linearLayoutManager = LinearLayoutManager(mainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(SpaceItemDecoration(30, 10))

        newsViewModel.getSavedNews()

        newsViewModel.getSavedArticlesResult().observe(mainActivity
        ) {
            if(it != null)
            {
                val adapter = NewsAdapter(it, mainActivity, false)
                recyclerView.adapter = adapter
            }
        }
    }

}