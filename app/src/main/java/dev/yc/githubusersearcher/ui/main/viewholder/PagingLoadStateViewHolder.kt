package dev.yc.githubusersearcher.ui.main.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dev.yc.githubusersearcher.databinding.ItemLoadingBinding

class PagingLoadStateViewHolder(
    private val binding: ItemLoadingBinding,
    private val retryCallback: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRefresh.setOnClickListener {
            retryCallback()
        }
    }

    fun onBind(loadState: LoadState) {
        with(binding) {
            pbLoading.isVisible = loadState is LoadState.Loading
            btnRefresh.isVisible = loadState is LoadState.Error

            tvMessage.run {
                isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}