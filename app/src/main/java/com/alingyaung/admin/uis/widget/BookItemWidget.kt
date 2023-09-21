package com.alingyaung.admin.uis.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alingyaung.admin.R
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState

@Composable
fun BookItemWidget(
    state:BookScreenState,
    onEvent:(BookScreenEvent)->Unit
){
    Box {
        Column {
            Image(
                modifier = Modifier.fillMaxSize()
                    .height(50.dp)
                    .padding(20.dp),
                painter = painterResource(R.drawable.notice),
                contentDescription = null)

            Text(
                text = "Ks 45000",
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(300),
                    letterSpacing = 0.1.sp,
                )
            )
            Text(
                text = "Rrisoner of AZKABAN",
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(400),
                    letterSpacing = 0.1.sp,
                )
            )
        }

    }
}

@Preview(widthDp = 100, heightDp = 200, showBackground = true)
@Composable
fun BookItemPreview(){
    BookItemWidget(
        state = BookScreenState(listOf<Book>()),
        onEvent = {}
    )
}