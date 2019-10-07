package com.githubrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.githubrepositories.R
import com.githubrepositories.databinding.FragmentDetailsBinding
import com.githubrepositories.domain.DetailsInfoModel
import com.githubrepositories.viewmodels.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel: DetailsViewModel by lazy {
        val activity = requireNotNull(this.activity) { }
        ViewModelProviders.of(this, DetailsViewModel.Factory(activity.application))
            .get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        binding.let {
            it.setLifecycleOwner(viewLifecycleOwner)
            val nameValue: String? = activity?.intent?.getStringExtra("name")
            val avatar: String? = activity?.intent?.getStringExtra("avatar")

            it.detailInfoModel = DetailsInfoModel(nameValue, avatar)

            var isToolbarShown = false

            it.detailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    val shouldShowToolbar = scrollY > toolbar.height

                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar
                        appbar.isActivated = shouldShowToolbar
                        binding.toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            it.toolbar.setNavigationOnClickListener {
                this.activity?.onBackPressed()
            }
        }

        return binding.root
    }
}