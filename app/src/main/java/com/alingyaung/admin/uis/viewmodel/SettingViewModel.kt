package com.alingyaung.admin.uis.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.data.repository.MainRepository
import com.alingyaung.admin.presentation.event.SettingEvent
import com.alingyaung.admin.presentation.state.SettingScreenUiState
import com.alingyaung.admin.utils.extension.validate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    private val _state = mutableStateOf(SettingScreenUiState())
    val state: State<SettingScreenUiState> = _state

    fun onEvent(event: SettingEvent){
        when(event){
            is SettingEvent.SubmitAuthor -> submitAuthor()
            is SettingEvent.SubmitCategory -> submitCategory()
            is SettingEvent.SubmitGenre -> TODO()
            is SettingEvent.SubmitPublisher -> submitPublisher()
            is SettingEvent.InputChangeEvent -> _state.value = _state.value.copy(name = event.text)
        }

    }

    private fun submitAuthor(){
        if(!validate()){
            return
        }
        val authorData = Author(
            id = UUID.randomUUID().toString(),
            name = _state.value.name,
            bio = null,
            image = null
        )
        viewModelScope.launch{
            try {
                delay(500L)
                _isLoading.value= true
                repository.addAuthor(authorData).collect{
                    Log.d("result",it)
                    _isLoading.value = false
                    _state.value = _state.value.copy(name = "")
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }

        }
    }
    private fun submitCategory() {
        if(!validate()){
            return
        }
        val categoryData = Category(
            id = UUID.randomUUID().toString(),
            name = _state.value.name
        )
        viewModelScope.launch {
            try {
                delay(500L)
                _isLoading.value = true
                repository.addCategory(categoryData).collect{
                    _isLoading.value = false
                    _state.value = _state.value.copy(name = "")
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun validate() : Boolean{
        val validateResult = _state.value.name.validate()
        val hasError = listOf(validateResult).any{
            !it.success
        }
        if(hasError){
            _state.value = _state.value.copy(
                nameError = validateResult.errorMessage ?: "required"
            )
            return false
        }
        return true
    }
    private fun submitPublisher() {
        if(!validate()){
            return
        }
        val publisherData = Publisher(
            id = UUID.randomUUID().toString(),
            name = _state.value.name
        )
        viewModelScope.launch {
            try {
                delay(500L)
                _isLoading.value = true
                repository.addPublisher(publisherData).collect{
                    _isLoading.value = false
                    _state.value = _state.value.copy(name = "")
                }
            }catch (e:Exception){

            }
        }
    }
}