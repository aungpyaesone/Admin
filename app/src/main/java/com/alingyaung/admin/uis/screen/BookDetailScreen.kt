package com.alingyaung.admin.uis.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import com.alingyaung.admin.R
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.utils.extension.getImageRequest

@Composable
fun BookDetailScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(dimensionResource(R.dimen.medium_dimen)),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            /* AsyncImage(
                 modifier = Modifier.width(dimensionResource(R.dimen.dimen_120))
                     .height(dimensionResource(R.dimen.dimen_150)),
                 model = getImageRequest(context,book.image),
                 contentDescription = null,
                 imageLoader = context.imageLoader
             )*/

            Image(
                painter = painterResource(R.drawable.test),
                modifier = Modifier.width(dimensionResource(R.dimen.dimen_120))
                    .height(dimensionResource(R.dimen.dimen_150)),
                contentDescription = null
            )

            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start){
                Text(
                    text = "ABCDEFG",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "HIJKLMN",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = "OPQRSTU",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_dimen)))
            Text(
                text = stringResource(R.string.introduction),
                modifier = Modifier.fillMaxWidth(),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.medium_dimen)))
            val contentText = stringResource(R.string.content)
            Text(
                text = buildAnnotatedString {
                    append(contentText)
                    addStyle(style = ParagraphStyle(textAlign = TextAlign.Justify)
                    ,0,contentText.length)
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BookScreenPreview() {
    Surface {
        BookDetailScreen()
    }
}