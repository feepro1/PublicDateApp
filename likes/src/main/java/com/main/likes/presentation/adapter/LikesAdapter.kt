package com.main.likes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.main.core.UsernameUi
import com.main.likes.R
import com.main.likes.data.entities.LikeFromUser
import com.main.likes.databinding.ItemLikeBinding
import com.main.likes.domain.ManageLikesAdapterData

class LikesAdapter(
    private val likeCLickListener: LikeCLickListener
) : RecyclerView.Adapter<LikesAdapter.LikesViewHolder>(), UsernameUi, ManageLikesAdapterData {
    private val likes = mutableListOf<LikeFromUser>()

    class LikesViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemLikeBinding.bind(item) }
        fun bind(likeFromUser: LikeFromUser, usernameUi: UsernameUi, likeCLickListener: LikeCLickListener) {
            binding.tvUsername.text = usernameUi.mapUsername(likeFromUser.firstName, likeFromUser.lastName)
            Glide.with(itemView).load(likeFromUser.avatarUrl).into(binding.ivUserIcon)
            binding.ivUserIcon.setOnClickListener { likeCLickListener.iconClick(likeFromUser) }
            binding.itemChat.setOnClickListener { likeCLickListener.itemClick(likeFromUser) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_like, parent, false)
        return LikesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikesViewHolder, position: Int) {
        holder.bind(likes[position], this, likeCLickListener)
    }

    override fun getItemCount() = likes.size

    override fun mapUsername(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }

    override fun mapAll(newLikes: List<LikeFromUser>) {
        val diff = LikeDiffUtilCallback(likes, newLikes)
        val result = DiffUtil.calculateDiff(diff)
        likes.clear()
        likes.addAll(newLikes)
        result.dispatchUpdatesTo(this)
    }
}

interface LikeCLickListener {

    fun iconClick(likeFromUser: LikeFromUser)

    fun itemClick(likeFromUser: LikeFromUser)
}

class LikeDiffUtilCallback(
    private val oldList: List<LikeFromUser>,
    private val newList: List<LikeFromUser>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].uid == (newList[newItemPosition]).uid

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}