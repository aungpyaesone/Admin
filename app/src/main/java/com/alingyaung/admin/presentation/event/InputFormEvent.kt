package com.alingyaung.admin.presentation.event

import android.graphics.Bitmap
import com.alingyaung.admin.domain.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.domain.Publisher

sealed class InputFormEvent : BaseEvent{
    data class NameChange(val name:String) : InputFormEvent()
    data class AuthorChange(val name:String) : InputFormEvent()
    data class AuthorVOChange(val author:Author) : InputFormEvent()
    data class CategoryChange(val name:String) : InputFormEvent()
    data class CategoryVOChange(val category: Category) : InputFormEvent()
    data class  GenderChange(val gender:String) : InputFormEvent()
    data class GenreVOChange(val genre: Genre) : InputFormEvent()
    data class IsbnChange(val name:String) : InputFormEvent()
    data class PriceChange(val price: Double) : InputFormEvent()
    data class StockChange(val name:Int) : InputFormEvent()
    data class PublicDateChange(val name:String) : InputFormEvent()
    data class DescriptionChange(val name:String) : InputFormEvent()
    data class ImageChange(val name:String) : InputFormEvent()
    data class PublisherChange(val name:String) : InputFormEvent()
    data class PublisherVOChange(val publisher: Publisher) : InputFormEvent()
     object Submit: InputFormEvent()
    object SubmitAuthor : InputFormEvent()
    object SubmitGenre : InputFormEvent()

    object SubmitPublisher: InputFormEvent()
    object SubmitCategory : InputFormEvent()

    object GetAuthorEvent : InputFormEvent()

    data class SendImageEvent( val bitmap: Bitmap?) : InputFormEvent()
}
