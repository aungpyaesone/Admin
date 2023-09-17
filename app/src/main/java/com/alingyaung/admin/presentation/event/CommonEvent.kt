package com.alingyaung.admin.presentation.event

sealed class CommonEvent : BaseEvent{
    object GetAuthorEvent : CommonEvent()
    object GetGenreEvent : CommonEvent()
    object GetCategoryEvent : CommonEvent()
    object GetPublisherEvent : CommonEvent()

    data class SearchTextEvent(val text:String) : CommonEvent()
}