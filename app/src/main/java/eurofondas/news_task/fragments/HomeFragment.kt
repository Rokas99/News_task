package eurofondas.news_task.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import eurofondas.news_task.R
import eurofondas.news_task.adapters.NewsAdapter
import eurofondas.news_task.connection.NetworkConnectionLiveData
import eurofondas.news_task.decoration.SpaceItemDecoration
import eurofondas.news_task.viewmodels.NewsViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_home_recyclerView)

        val linearLayoutManager = LinearLayoutManager(requireActivity())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(SpaceItemDecoration(30, 10))

        val networkLiveData = NetworkConnectionLiveData(requireContext())

        networkLiveData.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                Log.d("Available", "Available")
                newsViewModel.getNews()
            } else {
                Toasty.error(requireContext(), "Network error", Toast.LENGTH_LONG, true).show()
            }
        }

        newsViewModel.getNewsResult().observe(viewLifecycleOwner
        ) {
            if (it != null) {
                val articles = it.articles
                val adapter = NewsAdapter(articles, requireActivity(), true)
                recyclerView.adapter = adapter
            }
        }

        newsViewModel.getErrorsResult().observe(viewLifecycleOwner) {
            if (it != null) {
                Toasty.error(requireContext(), it, Toast.LENGTH_LONG, true).show()
            }
        }
    }
}



