package com.alingyaung.admin.widget

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.TypeSpecimen
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alingyaung.admin.domain.NavAddScreen
import com.alingyaung.admin.domain.NavigationItem
import com.alingyaung.admin.presentation.state.NavState


@Composable
fun NavItemWithText(
    data: NavAddScreen,
    onClickItem: (NavAddScreen) -> Unit
){
        Card (modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                onClickItem(data)
            }
        ){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(imageVector = data.selectedIcon, contentDescription = "add author")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = data.title)

                }
            }
    }
}

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun NavItemPreview(){
    NavItemWithText(
        NavAddScreen(
            route = "AddAuthor",
            title = "Add Author",
            selectedIcon = Icons.Default.PersonAdd,
            hasNews = false,
            badgeCount = null
        ),
        {}
    )
}