package com.alingyaung.admin.presentation.event

sealed class SettingEvent {
    object SubmitAuthor : SettingEvent()
    object SubmitPublisher: SettingEvent()
    object SubmitCategory : SettingEvent()
    object SubmitGenre : SettingEvent()

    data class InputChangeEvent(val text:String): SettingEvent()

}