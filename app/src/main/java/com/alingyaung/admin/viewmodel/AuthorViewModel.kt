package com.alingyaung.admin.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.data.remote.FireBaseApi
import com.alingyaung.admin.domain.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.DomainItem
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.domain.Publisher
import com.alingyaung.admin.presentation.event.CommonEvent
import com.alingyaung.admin.presentation.state.AuthorState
import com.alingyaung.admin.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel(){

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _state = mutableStateOf(AuthorState())
    val state: State<AuthorState> = _state

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading


    private val _domainItem =  MutableStateFlow(listOf<DomainItem>())
    val domainItem = searchText.combine(_domainItem){ text,domain ->
        if(text.isBlank()){
            domain
        }else{
            domain.filter {
                when(it){
                    is Author -> it.doMatchSearchQuery(it.name)
                    is Category -> it.doMatchSearchQuery(it.name)
                    is Genre -> it.doMatchSearchQuery(it.name)
                    is  Item-> it.doMatchSearchQuery(it.name)
                    is Publisher -> it.doMatchSearchQuery(it.name)
                    else -> { false}
                }
            }
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
        _domainItem.value)

    fun onEvent(event: CommonEvent){
            when(event){
                is CommonEvent.GetAuthorEvent ->{
                    getAuthorList()
                }
                is CommonEvent.GetCategoryEvent ->{
                    getCategoryList()
                }

                is CommonEvent.GetGenreEvent -> {
                    getGenreList()
                }

                is CommonEvent.GetPublisherEvent -> {
                    getPublisher()
                }

                is CommonEvent.SearchTextEvent->{
                    onSearchTextChange(event.text)
                }

            }
    }

    private fun getAuthorList(){
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllAuthors().collect{
                _state.value = _state.value.copy(authorList  = it)
                _isLoading.value = false
            }
        }
    }

    private fun onSearchTextChange(text:String){
        _searchText.value = text
    }


    private fun getCategoryList(){
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllCategoryList().collect{
                _state.value = _state.value.copy(categoryList  = it)
                _isLoading.value = false
            }
        }
    }
    private fun getGenreList(){
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllGenreList().collect{
                _state.value = _state.value.copy(genreList  = it)
                _isLoading.value = false
            }
        }
    }
    private fun getPublisher(){
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllPublisherList().collect{
                _state.value = _state.value.copy(publisherList  = it)
                _isLoading.value = false
            }
        }
    }
}