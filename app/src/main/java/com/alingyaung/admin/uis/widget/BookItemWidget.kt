package com.alingyaung.admin.uis.widget

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.alingyaung.admin.R
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.presentation.event.BookScreenEvent
import com.alingyaung.admin.presentation.state.BookScreenState
import com.alingyaung.admin.utils.extension.format
import com.alingyaung.admin.utils.extension.getImageRequest

@Composable
fun BookItemWidget(
    book:Book,
    onEvent:(BookScreenEvent)->Unit,
    modifier: Modifier= Modifier,
    onItemClick : () -> Unit
){
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxWidth().padding(8.dp)
        .clickable {
            Log.d("you click me", "hi hi hi")
            onItemClick.invoke()
        }){
        Column {
            AsyncImage(
                model = getImageRequest(context,book.image),
                contentDescription = "",
                imageLoader = context.imageLoader,
                modifier = Modifier.fillMaxWidth().
            width(120.dp).
            height(150.dp).
                padding(bottom = 8.dp)
            )

            Text(
                text = book.price.format(),
                style = TextStyle(
                    fontSize = 10.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(300),
                    letterSpacing = 0.1.sp,
                )
            )
            Text(
                text = book.name,
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
        book = Book(
            "","",
            "","","",
            "",0.0,0,
            0L,0L,"",
            ""
        ),
        onEvent = {},
        onItemClick = {}
    )
}