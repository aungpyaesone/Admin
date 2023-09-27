package com.alingyaung.admin.presentation.event

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.alingyaung.admin.data.persistence.entity.Author
import com.alingyaung.admin.data.persistence.entity.Category
import com.alingyaung.admin.domain.Genre
import com.alingyaung.admin.data.persistence.entity.Publisher

sealed class InputFormEvent : BaseEvent{
    data class NameChange(val name:String) : InputFormEvent()
    data class AuthorChange(val name:String) : InputFormEvent()
    data class AuthorVOChange(val author: Author) : InputFormEvent()
    data class CategoryChange(val name:String) : InputFormEvent()
    data class CategoryVOChange(val category: Category) : InputFormEvent()
    data class  GenderChange(val gender:String) : InputFormEvent()
    data class GenreVOChange(val genre: Genre) : InputFormEvent()
    data class IsbnChange(val name:String) : InputFormEvent()
    data class PriceChange(val price: Double) : InputFormEvent()
    data class StockChange(val stock:String) : InputFormEvent()
    data class PublicDateChange(val date:Long) : InputFormEvent()
    data class DescriptionChange(val name:String) : InputFormEvent()
    data class ImageChange(val name:String) : InputFormEvent()
    data class PublisherChange(val name:String) : InputFormEvent()
    data class PublisherVOChange(val publisher: Publisher) : InputFormEvent()
     object Submit: InputFormEvent()
    data class SubmitAuthor(val authorName: String) : InputFormEvent()
    object SubmitGenre : InputFormEvent()

    data class SubmitPublisher(var publisherName:String) : InputFormEvent()
    data class SubmitCategory(var categoryName:String) : InputFormEvent()

    object GetAuthorEvent : InputFormEvent()

    data class SendImageEvent( val bitmap: Bitmap?) : InputFormEvent()

    data class CompressImageEvent(val uri: Uri,val context:Context) : InputFormEvent()
}
