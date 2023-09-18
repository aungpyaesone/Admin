package com.alingyaung.admin.uis.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alingyaung.admin.presentation.event.CommonEvent
import com.alingyaung.admin.presentation.state.AuthorState


@Composable
fun AuthorListScreen(
    navHostController: NavHostController,
    context: Context,
    state: AuthorState,
    isLoading: Boolean,
    onEvent : (CommonEvent) -> Unit
) {

    LaunchedEffect(key1 = true) {
        //onEvent(CommonEvent.GetAuthorEvent)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            // Show a loading indicator here
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        } else {
            LazyColumn{
                items(state.authorList ?: emptyList()){
                  /*  ItemsWidget(it){data ->
                        Toast.makeText(context,data.name,Toast.LENGTH_SHORT).show()
                    }*/
                }
            }
          
        }
    }
}