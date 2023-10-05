package com.alingyaung.admin.presentation.event

import com.alingyaung.admin.uis.widget.SearchWidgetState

sealed class BookScreenEvent: BaseEvent {
    object GetBookListEvent : BookScreenEvent()

    data class BookFilterEvent(val keyword: String): BookScreenEvent()

    data class UpdateSearchEvent(val query:String): BookScreenEvent()
    data class UpdatedWidgetState(val searchWidgetState: SearchWidgetState): BookScreenEvent()

}