package com.raddyr.recruitmenttask.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raddyr.recruitmenttask.R
import com.raddyr.recruitmenttask.databinding.ListItemLayoutBinding

class ArticleAdapter() :
    ListAdapter<Article, ArticleAdapter.ListViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvDate.text = item.modificationDate
                Glide.with(itemView.context)
                    .load(item.image_url)
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(ivPhoto)
                root.setOnClickListener {
                    item.url?.let {
                        Navigation.findNavController(root)
                            .navigate(
                                ArticleListFragmentDirections.actionListFragmentToDetailsFragment(
                                    item.url
                                )
                            )
                    }
                }
            }
        }
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.orderId == newItem.orderId
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}