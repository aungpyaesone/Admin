package com.alingyaung.admin.uis.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alingyaung.admin.R
import com.alingyaung.admin.domain.Author
import com.alingyaung.admin.domain.Category
import com.alingyaung.admin.domain.DomainItem

//@Preview(widthDp = 300)
@Composable
fun <T: DomainItem> ItemsWidget(
    item: T,
    onClick: (T) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(0.5.dp, color = Color.Gray)
            .clickable {
                onClick(item)
            }
    ) {
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            Text(
                text = item.name,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                color = Color.Black
            )
            /*when(item ){

                is Author ->
                is Category -> Text(text = )
            }*/

        }
    }
}
