package com.alingyaung.admin.presentation.event

sealed class BookScreenEvent: BaseEvent {
    object GetBookListEvent : BookScreenEvent()

    data class BookFilterEvent(val keyword: String): BookScreenEvent()
}