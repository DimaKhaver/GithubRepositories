package com.listofreposgithub.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.listofreposgithub.databinding.FragmentListItemBinding
import com.listofreposgithub.domain.UserInfoModel
import com.listofreposgithub.utils.DETAILS_DYNAMIC_FEATURE_PATH

class CustomAdapter : ListAdapter<UserInfoModel, CustomAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder constructor(private val binding: FragmentListItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener { view ->
                val intent = Intent().setClassName(view.context, DETAILS_DYNAMIC_FEATURE_PATH)
                intent.putExtra("name", binding.userInfoModel?.login)
                intent.putExtra("avatar", binding.userInfoModel?.avatar)
                binding.root.context.startActivity(intent)
            }
        }

        fun bind(userInfoModel: UserInfoModel) {
            binding.userInfoModel = userInfoModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FragmentListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<UserInfoModel>() {

    override fun areItemsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserInfoModel, newItem: UserInfoModel): Boolean {
        return oldItem == newItem
    }
}

