package eurofondas.news_task.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import eurofondas.news_task.R
import eurofondas.news_task.fragments.HomeFragment
import eurofondas.news_task.fragments.SavedFragment
import eurofondas.news_task.viewmodels.NewsViewModel
import nl.joery.animatedbottombar.AnimatedBottomBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction()
            .replace(R.id.activity_main_fragment_container, HomeFragment(), "HomeFragment").commit()

        val bottomBar = findViewById<AnimatedBottomBar>(R.id.activity_main_bottom_bar)

        bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if (newIndex == 0) {
                    fragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, HomeFragment(), "HomeFragment").commit()
                }
                else if (newIndex == 1) {
                    fragmentManager.beginTransaction()
                        .replace(R.id.activity_main_fragment_container, SavedFragment(), "SavedFragment").commit()
                }
            }

        })
    }
}