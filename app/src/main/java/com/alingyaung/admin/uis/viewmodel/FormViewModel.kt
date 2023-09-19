package com.alingyaung.admin.uis.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Item
import com.alingyaung.admin.data.persistence.entity.Publisher
import com.alingyaung.admin.presentation.event.InputFormEvent
import com.alingyaung.admin.presentation.state.AuthorState
import com.alingyaung.admin.presentation.state.InputFormState
import com.alingyaung.admin.utils.extension.validate
import com.alingyaung.admin.data.repository.MainRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _state = mutableStateOf(InputFormState())
    val state: State<InputFormState> = _state

    private val _state2 = mutableStateOf(AuthorState())
    val state2: State<AuthorState> = _state2

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    // Define a MutableState for the list of items
    private val _itemList = mutableStateListOf<String>()
    val itemList: List<String> get() = _itemList

    // Function to add an item to the list
    fun addItemToList(item: String ) {
        _itemList.add(item)
    }

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
                uploadImage(bitmap = event.bitmap)
            }
            is InputFormEvent.SubmitAuthor->{
                submitAuthor(event.authorName)
            }
            is InputFormEvent.GetAuthorEvent ->{
              //  getAuthorList()
            }

            is InputFormEvent.GenderChange -> _state.value = _state.value.copy(genre = event.gender)
            is InputFormEvent.SubmitCategory -> submitCategory(event.categoryName)
            is InputFormEvent.SubmitGenre -> submitGenre()
            is InputFormEvent.SubmitPublisher -> submitPublisher(event.publisherName)
            is InputFormEvent.AuthorVOChange -> {
                _state.value = _state.value.copy(
                    authorVO = event.author
                )
            }
            is InputFormEvent.GenreVOChange -> _state.value = _state.value.copy(genreVO = event.genre)
            is InputFormEvent.CategoryVOChange -> _state.value = _state.value.copy(categoryVO = event.category)
            is InputFormEvent.PublisherVOChange -> _state.value = _state.value.copy(publisherVO = event.publisher)
            else ->{}
        }
    }

    private fun submitGenre() {
        val genreResult = _state.value.genre.validate()
        val hasError = listOf(genreResult).any{
            !it.success
        }
        if(hasError){
            _state.value = _state.value.copy(
                genreError = genreResult.errorMessage
            )
            return
        }
        val genreData = Genre(
            id = UUID.randomUUID().toString(),
            name = _state.value.genre
        )
        viewModelScope.launch {
            try {
                delay(500L)
                _isLoading.value = true
                repository.addGenre(genreData).collect{
                    _isLoading.value = false
                    _state.value = _state.value.copy(genre = "")
                }
            }catch (e:Exception){

            }
        }
    }

    private fun submitPublisher(publisherName:String) {
        /*val genreResult = _state.value.publisher.validate()
        val hasError = listOf(genreResult).any{
            !it.success
        }
        if(hasError){
            _state.value = _state.value.copy(
                genreError = genreResult.errorMessage
            )
            return
        }*/
        val publisherData = Publisher(
            id = UUID.randomUUID().toString(),
            name = publisherName
        )
        viewModelScope.launch {
            try {
                delay(500L)
                _isLoading.value = true
                repository.addPublisher(publisherData).collect{
                    _isLoading.value = false
                    _state.value = _state.value.copy(publisher = "")
                }
            }catch (e:Exception){

            }
        }
    }

    private fun submitCategory(categoryName:String) {
        /*val categoryResult = _state.value.category.validate()
        val hasError = listOf(categoryResult).any{
            !it.success
        }
        if(hasError){
            _state.value = _state.value.copy(
                genreError = categoryResult.errorMessage
            )
            return
        }*/
        val categoryData = Category(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )
        viewModelScope.launch {
            try {
                delay(500L)
                _isLoading.value = true
                repository.addCategory(categoryData).collect{
                    _isLoading.value = false
                    _state.value = _state.value.copy(category = "")
                }
            }catch (e:Exception){

            }
        }
    }

    private fun submitFormData() {
        val nameResult = _state.value.name.validate()
        val authorResult = _state.value.authorVO.name.validate()
        val categoryResult = _state.value.categoryVO.name.validate()
        val priceResult = _state.value.price.validate()
        val stockResult = _state.value.stock.validate()
        val publishDateResult = _state.value.publication_date.validate()
        val imageResult = _state.value.imageUrl.validate()
        val publisherResult = _state.value.publisherVO.name.validate()
        val genreResult = _state.value.genreVO.name.validate()
        Log.d("author_id",_state.value.authorVO.name)
        // val

        val hasError = listOf(
            nameResult,
            authorResult,
            categoryResult,
            priceResult,
            stockResult,
            publisherResult,
            publishDateResult,
            genreResult
        ).any {
            !it.success
        }
        if (hasError) {
            _state.value = _state.value.copy(
                nameError = nameResult.errorMessage,
                authorError = authorResult.errorMessage,
                categoryError = categoryResult.errorMessage,
                priceError = priceResult.errorMessage,
                stockError = stockResult.errorMessage,
                publisherError = publisherResult.errorMessage,
                publication_dateError = publishDateResult.errorMessage,
                genreError = genreResult.errorMessage
            )
            return
        }

        Log.d("result2",Gson().toJson(_state.value))
        val bookData = Book(
            id = UUID.randomUUID().toString(),
            author_id = _state.value.authorVO.id,
            isbn = _state.value.isbn,
            category_id = _state.value.categoryVO.id,
            price = _state.value.price,
            stock = _state.value.stock,
            publication_date = _state.value.publication_date,
            publisher_id = _state.value.publisherVO.id,
            description = _state.value.description,
            image = _state.value.imageUrl,
            genre_id = _state.value.genreVO.id,
            name = _state.value.name,
            created_date = System.currentTimeMillis()
        )
        Log.d("result",Gson().toJson(bookData))
        viewModelScope.launch {
            _isLoading.value= true
            repository.addBook(bookData).collect{
                _isLoading.value = false
            }
           // validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun submitAuthor(authorName: String){
        /*val nameResult = _state.value.name.validate()
        val hasError = listOf(nameResult).any{
            !it.success
        }
        if(hasError){
            _state.value = _state.value.copy(
                nameError = nameResult.errorMessage
            )
            return
        }*/

        val authorData = Author(
            id = UUID.randomUUID().toString(),
            name = authorName,
            bio = null,
            image = null
        )
        viewModelScope.launch{
            try {
                delay(500L)
       //         validationEventChannel.send(ValidationEvent.Loading)
                _isLoading.value= true
               repository.addAuthor(authorData).collect{
                   Log.d("result",it)
                   _isLoading.value = false
                   _state.value = _state.value.copy(name = "")
               }
            }
            catch (e: Exception){

            }

        }
    }

    private fun submitImage(){

    }

    private fun getAuthorList(){
        viewModelScope.launch {
            _isLoading .value = true
            repository.getAllAuthors().collect{
                _state2.value.authorList = it.data
                _isLoading.value = false
            }
        }
    }


    private fun uploadImage(bitmap: Bitmap?){
        viewModelScope.launch {
            bitmap?.let {
                _isLoading.value = true
                repository.uploadImage(bitmap).collect{
                    _state.value = _state.value.copy(imageUrl = it)
                    _isLoading.value = false
                }
            }
        }
    }

}



sealed class ValidationEvent {
    object  Loading : ValidationEvent()
    object Success : ValidationEvent()
    data class Failure(val errorMsg:String) : ValidationEvent()
}