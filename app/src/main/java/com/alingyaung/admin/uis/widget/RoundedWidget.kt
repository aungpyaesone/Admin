package com.alingyaung.admin.uis.widget

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alingyaung.admin.R

@Composable
fun RoundedWidget(){
    Column(
        verticalArrangement = Arrangement.spacedBy(11.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Child views.
        Image(
            painter = painterResource(id = R.drawable.notice),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = "George",
            style = TextStyle(
                fontSize = 10.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(300),
                color = Color(0xFF000000),
                letterSpacing = 0.1.sp,
            )
        )
    }


//    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center){
//            Icon(painter = painterResource(R.drawable.notice),contentDescription = null)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = "Aung Pyae Sone")
//
//        }
//    }

}

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun RoundedWidgetPreview(){
    RoundedWidget()
}