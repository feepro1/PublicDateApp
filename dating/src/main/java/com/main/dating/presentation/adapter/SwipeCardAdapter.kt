package com.main.dating.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.main.dating.R
import com.main.dating.data.entities.User
import com.main.dating.databinding.ItemCardStackViewBinding
import com.main.dating.domain.ManageCardStackView
import com.main.dating.presentation.UsernameUi

class SwipeCardAdapter(
    private val manageUserClickListener: ManageUserClickListener
) : RecyclerView.Adapter<SwipeCardAdapter.SwipeCardViewHolder>(), ManageCardStackView, UsernameUi {
   val users = mutableListOf(
        User("Hello", "World", "dsajkdas", "dasdas"),
        User("Hello1", "World", "dsajkdas", "dasdas"),
        User("Hello2", "World", "dsajkdas", "dasdas"),
        User("Hello3", "World", "dsajkdas", "dasdas")
    )

    class SwipeCardViewHolder(item: View): ViewHolder(item) {
        private val binding by lazy { ItemCardStackViewBinding.bind(item) }

        fun bind(user: User, manageUserClickListener: ManageUserClickListener, usernameUi: UsernameUi) {
            binding.fabLike.setOnClickListener { manageUserClickListener.clickLike(user) }
            binding.fabDislike.setOnClickListener { manageUserClickListener.clickDislike(user) }
            binding.tvUsername.text = usernameUi.mapUsername(user.firstName, user.lastName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_stack_view, parent, false)
        return SwipeCardViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: SwipeCardViewHolder, position: Int) {
        holder.bind(users[position], manageUserClickListener, this)
    }

    override fun mapAll(value: List<User>) {
        val diff = DiffUtilCallback(users, value)
        val result = DiffUtil.calculateDiff(diff)
        users.clear()
        users.addAll(value)
        result.dispatchUpdatesTo(this)
    }

    override fun mapUsername(firstName: String, lastName: String): String {
        return "$firstName $lastName"
    }
}

interface ManageUserClickListener {

    fun clickLike(user: User)

    fun clickDislike(user: User)
}

class DiffUtilCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].email == (newList[newItemPosition]).email

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}