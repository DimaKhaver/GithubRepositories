package com.listofreposgithub.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.listofreposgithub.R
import com.listofreposgithub.databinding.FragmentListBinding
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.ui.adapters.CustomAdapter
import com.listofreposgithub.utils.toast
import com.listofreposgithub.viewmodels.ListOfReposViewModel

class RepositoriesFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: CustomAdapter

    private val viewModel: ListOfReposViewModel by lazy {
        val activity = requireNotNull(this.activity) { }
        ViewModelProviders.of(this, ListOfReposViewModel.Factory(activity.application))
            .get(ListOfReposViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.setLifecycleOwner(viewLifecycleOwner)
        adapter = CustomAdapter()
        binding.repositoriesList.adapter = adapter

        setUpAvailableData(adapter)
        setUpSwipeToRefreshLogic()

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setUpSwipeToRefreshLogic() {
        binding.swipeLayout.run {
            this.setColorSchemeColors(R.color.colorPrimary, R.color.black_color)
            this.setOnRefreshListener {
                viewModel.setUpData(this@RepositoriesFragment)
            }
        }
    }

    private fun setUpAvailableData(adapter: CustomAdapter) {
        viewModel.setUpData(this)
        viewModel.repositoryData.observe(this, Observer<List<UserInfoModel>> {
            binding.swipeLayout.isRefreshing = false
            if (it.isNullOrEmpty()) {
                context?.toast("Turn on internet to load data!")
                binding.isEmpty = true
            } else {
                binding.isEmpty = false
                adapter.submitList(it)
            }
        })
    }
}