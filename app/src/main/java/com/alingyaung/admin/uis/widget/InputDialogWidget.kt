package com.alingyaung.admin.uis.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.alingyaung.admin.domain.DomainItem

@Composable
fun InputDialogWidget(
    showDialog: Boolean,
    title: String,
    setShowDialog: (Boolean) -> Unit,
){
    if (showDialog) {
        Dialog(
            onDismissRequest = { setShowDialog(false) },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Set the width of the dialog
                        .height(300.dp) // Set the height of the dialog
                        .background(color = Color.White)
                    // Set the background color of the dialog
                ) {
                    Column(modifier = Modifier.padding(start=8.dp, end = 8.dp)){
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = title,
                                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    color = Color.Black
                                )
                                Icon(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            setShowDialog(false)
                                        }, imageVector = Icons.Default.Close, contentDescription = null
                                )
                            }
                        }

                        TextField(value = "", onValueChange = {

                        })

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd){
                            Row(
                                modifier = Modifier
                                .padding(8.dp)){
                                Text(text = "Cancel")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Ok")
                            }
                        }

                    }

                }
            }
        }
    }
}

@Preview(widthDp = 200, heightDp = 200)
@Composable
fun InputDialogPreview(){
    InputDialogWidget(showDialog = true, title = "title",  setShowDialog ={} )
}