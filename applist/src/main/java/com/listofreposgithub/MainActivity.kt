package com.listofreposgithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listofreposgithub.R
import com.listofreposgithub.viewmodels.ListOfReposViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val viewModel: ListOfReposViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, ListOfReposViewModel.Factory(activity.application))
            .get(ListOfReposViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        setUpRecyclerViewOptions()

        viewModel.getDataFromInternet()
    }

    private fun setUpRecyclerViewOptions() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(emptyArray())

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
