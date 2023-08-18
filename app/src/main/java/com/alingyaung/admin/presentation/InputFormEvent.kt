package com.alingyaung.admin.presentation

sealed class InputFormEvent{
    data class NameChange(val name:String) : InputFormEvent()
    data class AuthorChange(val name:String) : InputFormEvent()
    data class CategoryChange(val name:String) : InputFormEvent()
    data class IsbnChange(val name:String) : InputFormEvent()
    data class PriceChange(val price: Double) : InputFormEvent()
    data class StockChange(val name:Int) : InputFormEvent()
    data class PublicDateChange(val name:String) : InputFormEvent()
    data class DescriptionChange(val name:String) : InputFormEvent()
    data class ImageChange(val name:String) : InputFormEvent()
    data class PublisherChange(val name:String) : InputFormEvent()
    object Submit: InputFormEvent()

    data class SendImageEvent(val imageUrl:String) : InputFormEvent()
}
