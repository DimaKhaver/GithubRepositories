package com.listofreposgithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listofreposgithub.R
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.utils.toast
import com.listofreposgithub.viewmodels.ListOfReposViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val viewModel: ListOfReposViewModel by lazy {
        val activity = requireNotNull(this) { }
        ViewModelProviders.of(
            this,
            ListOfReposViewModel.Factory(activity.application)
        )
            .get(ListOfReposViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerViewOptions()
        setUpAvailableData(viewModel)
    }

    private fun setUpAvailableData(viewModel: ListOfReposViewModel) {
        viewModel.setUpData(this)
        viewModel.repositoryData.observe(this, Observer<List<UserInfoModel>> {
            it?.let {
                if (it.isNullOrEmpty())
                    toast("All rsc are empty!")
            }
        })

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
