package dev.yc.githubusersearcher.ui.main.viewholder

import androidx.recyclerview.widget.RecyclerView
import dev.yc.githubusersearcher.databinding.ItemUserBinding
import dev.yc.githubusersearcher.model.user.User

class UserViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(user: User) {
        binding.user = user
    }
}