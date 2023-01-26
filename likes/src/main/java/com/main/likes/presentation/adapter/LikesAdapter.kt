package com.main.likes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.main.core.UserInfoUi
import com.main.core.entities.Like
import com.main.likes.R
import com.main.likes.databinding.ItemLikeBinding
import com.main.likes.domain.ManageLikesAdapterData

class LikesAdapter(
    private val likeCLickListener: LikeCLickListener
) : RecyclerView.Adapter<LikesAdapter.LikesViewHolder>(), UserInfoUi, ManageLikesAdapterData {
    private val likes = mutableListOf<Like>()

    class LikesViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemLikeBinding.bind(item) }
        fun bind(like: Like, userInfoUi: UserInfoUi, likeCLickListener: LikeCLickListener) {
            binding.tvUserInfo.text = userInfoUi.mapUserInfo(
                firstName = like.firstName, lastName = like.lastName,
                age = like.age, city = like.city
            )
            Glide.with(itemView).load(like.avatarUrl).into(binding.ivUserIcon)
            binding.ivUserIcon.setOnClickListener { likeCLickListener.iconClick(like) }
            binding.btnWrite.setOnClickListener { likeCLickListener.buttonWriteClick(like) }
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


    override fun mapAll(newLikes: List<Like>) {
        val diff = LikeDiffUtilCallback(likes, newLikes)
        val result = DiffUtil.calculateDiff(diff)
        likes.clear()
        likes.addAll(newLikes)
        result.dispatchUpdatesTo(this)
    }

    override fun mapUserInfo(firstName: String, lastName: String, age: Int, city: String): String {
        return "$firstName $lastName, $age, $city"
    }
}

interface LikeCLickListener {

    fun iconClick(like: Like)

    fun buttonWriteClick(like: Like)
}

class LikeDiffUtilCallback(
    private val oldList: List<Like>,
    private val newList: List<Like>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].message == (newList[newItemPosition]).message &&
        oldList[oldItemPosition].isMutualLike == (newList[newItemPosition]).isMutualLike

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}