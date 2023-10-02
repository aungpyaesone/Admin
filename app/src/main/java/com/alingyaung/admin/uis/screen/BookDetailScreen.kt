package com.alingyaung.admin.uis.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.imageLoader
import com.alingyaung.admin.R
import com.alingyaung.admin.data.persistence.entity.Book
import com.alingyaung.admin.presentation.event.BookDetailEvent
import com.alingyaung.admin.presentation.state.BookDetailUIState
import com.alingyaung.admin.utils.extension.format
import com.alingyaung.admin.utils.extension.getImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    bookId: String,
    state: BookDetailUIState,
    onEvent: (BookDetailEvent) -> Unit
) {
    val scrollBehavior2 = TopAppBarDefaults.enterAlwaysScrollBehavior()
    LaunchedEffect(null) {
        onEvent(BookDetailEvent.GetBookByIdEvent(bookId))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Card(
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.medium_dimen))
                            .background(MaterialTheme.colorScheme.onTertiary)
                            .clickable {
                                navController.popBackStack()
                            },
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_dimen)),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                                .padding(
                                    dimensionResource(id = R.dimen.small_dimen)
                                )
                        )
                    }

                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Mark as favorite"
                        )
                    }
                },
                scrollBehavior = scrollBehavior2
            )
        }
    ) {
        Surface(modifier = Modifier.padding(it)) {
            val context = LocalContext.current
            Log.d("bookId", bookId.toString())

            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(dimensionResource(R.dimen.medium_dimen)),
                contentAlignment = Alignment.TopStart
            ) {
                Column {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
//                    AsyncImage(
//                        modifier = Modifier.width(dimensionResource(R.dimen.dimen_120))
//                            .height(dimensionResource(R.dimen.dimen_150)),
//                        model = getImageRequest(context, state.book?.image),
//                        contentDescription = null,
//                        imageLoader = context.imageLoader
//                    )
                        Image(
                            painter = painterResource(R.drawable.test),
                            modifier = Modifier.width(dimensionResource(R.dimen.dimen_120))
                                .height(dimensionResource(R.dimen.dimen_150)),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = dimensionResource(R.dimen.small_dimen)),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = state.book?.name ?: "",
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Black
                            )
                            Log.d("price", state.book?.price.toString())
                            Text(
                                text = state.book?.price.format(),
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
                            addStyle(
                                style = ParagraphStyle(textAlign = TextAlign.Justify), 0, contentText.length
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Justify,
                    )

                }
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun BookScreenPreview() {
    Surface {
        BookDetailScreen(rememberNavController(), "",
            state = BookDetailUIState(),
            onEvent = {})
    }
}