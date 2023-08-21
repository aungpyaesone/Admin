package com.alingyaung.admin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alingyaung.admin.BR.items
import com.alingyaung.admin.di.AppModule.navigationItems
import com.alingyaung.admin.domain.NavigationItemsList
import com.alingyaung.admin.screen.FormViewModel
import com.alingyaung.admin.screen.InputFormScreen
import com.alingyaung.admin.screen.ValidationEvent
import com.alingyaung.admin.ui.theme.AdminTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdminTheme {
                // A surface container using the 'background' color from the theme
                val navigationItems = navigationItems()
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
                val focusRequester = remember { FocusRequester() }
                val interactionSource = remember { MutableInteractionSource() }
                val viewModel = viewModel<FormViewModel>()
                val state = viewModel.state
                val context = LocalContext.current
                LaunchedEffect(key1 = context) {
                    viewModel.validationEvents.collect { event ->
                        when (event) {
                            is ValidationEvent.Success -> {
                                Toast.makeText(
                                    context,
                                    "Registration successful",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            TopAppBar(
                                title = { Text(text = stringResource(id = R.string.title)) },
                                scrollBehavior = scrollBehavior
                            )
                        },
                        bottomBar = {
                           NavigationItemsList(
                               navigationItems = navigationItems,
                               currentSelectedItem = "",
                               onItemSelected = {}
                           )
                        }
                    ) { values ->
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(values)
                        ) {
                            item {
                                InputFormScreen(viewModel = viewModel,state = state.value)
                            }

                        }
                        /*
                    InputFormScreen(
                        viewModel = viewModel,
                        state = state.value
                    )*/
                    }
                }
            }
        }}}
