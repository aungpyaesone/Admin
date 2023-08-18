package com.alingyaung.admin.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.presentation.InputFormEvent
import com.alingyaung.admin.presentation.InputFormState
import com.alingyaung.admin.validate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FormViewModel() : ViewModel() {

    private val _state = mutableStateOf(InputFormState())
    val state: State<InputFormState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: InputFormEvent) {
        when (event) {
            is InputFormEvent.NameChange -> {
                _state.value = _state.value.copy(name = event.name)
            }
            is InputFormEvent.AuthorChange -> {
                _state.value = _state.value.copy(author = event.name)
            }
            is InputFormEvent.Submit -> {
                submitFormData()
            }
            is InputFormEvent.CategoryChange -> _state.value = _state.value.copy(category = event.name)
            is InputFormEvent.DescriptionChange -> _state.value = _state.value.copy(description = event.name)
            is InputFormEvent.ImageChange -> _state.value = _state.value.copy(imageUrl = event.name)
            is InputFormEvent.IsbnChange -> _state.value = _state.value.copy(isbn = event.name)
            is InputFormEvent.PriceChange -> _state.value = _state.value.copy(price = event.price)
            is InputFormEvent.PublicDateChange -> _state.value = _state.value.copy(publication_date = event.name)
            is InputFormEvent.PublisherChange -> _state.value = _state.value.copy(publisher = event.name)
            is InputFormEvent.StockChange -> _state.value = _state.value.copy(stock = event.name)
            is InputFormEvent.SendImageEvent -> {
                viewModelScope.launch {

                }
            }
        }
    }

    private fun submitFormData() {
        val nameResult = _state.value.name.validate()
        val authorResult = _state.value.author.validate()
        // val

        val hasError = listOf(
            nameResult,
            authorResult
        ).any {
            !it.success
        }

        if (hasError) {
            _state.value = _state.value.copy(
                nameError = nameResult.errorMessage,
                authorError = authorResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }
}

sealed class ValidationEvent {
    object Success : ValidationEvent()
}