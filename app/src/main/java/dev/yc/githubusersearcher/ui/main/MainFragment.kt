package dev.yc.githubusersearcher.ui.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dev.yc.githubusersearcher.databinding.MainFragmentBinding
import dev.yc.githubusersearcher.ui.main.adapter.PagingLoadStateAdapter
import dev.yc.githubusersearcher.ui.main.adapter.UserAdapter
import dev.yc.githubusersearcher.utils.hideKeyboard
import dev.yc.githubusersearcher.utils.livedata.EventObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment(), TextView.OnEditorActionListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupViews()
        setupEvents()
    }

    private fun setupViews() {
        setupSearcher()
        setupPaging()
    }

    private fun setupSearcher() {
        binding.tilSearch.editText?.setOnEditorActionListener(this@MainFragment)
    }

    private fun setupPaging() {
        val userAdapter = UserAdapter()
        binding.rvUsers.adapter = userAdapter.withLoadStateFooter(
            footer = PagingLoadStateAdapter(userAdapter)
        )

        setupLoadStateEventFromAdapter(userAdapter)
        setupPagingData(userAdapter)
    }

    private fun setupLoadStateEventFromAdapter(userAdapter: UserAdapter) {
        lifecycleScope.launch {
            userAdapter.loadStateFlow.collectLatest {
                // FIXME: Not working now
                val isNoDataWithError =
                    it.append is LoadState.NotLoading && it.refresh is LoadState.Error
                binding.tilSearch.isErrorEnabled = isNoDataWithError
            }
        }
    }

    private fun setupPagingData(userAdapter: UserAdapter) {
        viewModel.githubUsers.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                userAdapter.submitData(it)
            }
        }
    }

    private fun setupEvents() {
        setupHideKeyboardEvent()
    }

    private fun setupHideKeyboardEvent() {
        viewModel.canHideKeyboardEvent.observe(viewLifecycleOwner, EventObserver {
            requireActivity().hideKeyboard()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            viewModel.triggerHideKeyboardEvent()

            val keyword = v?.text.toString()
            viewModel.searchUser(keyword)

            return true
        }
        return false
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
