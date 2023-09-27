package com.alingyaung.admin.utils.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toEpochMilli(): Long{
    val instance = this.atStartOfDay(ZoneId.systemDefault()).toInstant()
    return instance.toEpochMilli()
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long?.toLocalDateAsString(pattern:String) : String?{
    val instant = this?.let { Instant.ofEpochMilli(it) }
    val localDate = instant?.atZone(ZoneId.systemDefault())?.toLocalDate()
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return localDate?.format(formatter)
}