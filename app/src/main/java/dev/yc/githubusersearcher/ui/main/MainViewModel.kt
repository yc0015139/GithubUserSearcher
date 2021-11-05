package dev.yc.githubusersearcher.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dev.yc.githubusersearcher.data.repository.SearchRepository
import dev.yc.githubusersearcher.data.repository.SearchRepositoryImpl
import dev.yc.githubusersearcher.model.user.User
import dev.yc.githubusersearcher.utils.livedata.Event
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    private val searchRepository: SearchRepository = SearchRepositoryImpl()
) : ViewModel() {

    private val _githubUsers = MutableLiveData<PagingData<User>>()
    val githubUsers: LiveData<PagingData<User>> get() = _githubUsers

    private val _canHideKeyboardEvent = MutableLiveData<Event<Boolean>>()
    val canHideKeyboardEvent: LiveData<Event<Boolean>> get() = _canHideKeyboardEvent

    fun searchUser(keyword: String) {
        viewModelScope.launch {
            searchRepository
                .searchGithubUsers(keyword)
                .cachedIn(this)
                .collectLatest {
                    _githubUsers.value = it
                }
        }
    }

    fun triggerHideKeyboardEvent() {
        _canHideKeyboardEvent.postValue(Event(true))
    }
}