package dev.yc.githubusersearcher.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import dev.yc.githubusersearcher.databinding.ItemLoadingBinding
import dev.yc.githubusersearcher.ui.main.viewholder.PagingLoadStateViewHolder

class PagingLoadStateAdapter(
    private val adapter: UserAdapter
) : LoadStateAdapter<PagingLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingLoadStateViewHolder(
            binding = ItemLoadingBinding.inflate(layoutInflater, parent, false),
            retryCallback = { adapter.retry() },
        )
    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.onBind(loadState)
    }
}
