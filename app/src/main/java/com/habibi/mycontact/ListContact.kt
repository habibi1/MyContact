package com.habibi.mycontact

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.habibi.mycontact.ui.common.ListViewEvent
import com.habibi.mycontact.ui.components.ContactItem
import com.habibi.mycontact.ui.components.EmptyLayout
import com.habibi.mycontact.ui.components.TopAppBar
import com.habibi.mycontact.ui.components.SearchBar
import com.habibi.mycontact.ui.components.SettingsDialog
import kotlin.math.roundToInt

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLifecycleComposeApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ListContact(
    viewModel: MainViewModel,
    onProfileClick: () -> Unit,
    onItemClicked: (String) -> Unit
) {
    val viewState = viewModel.consumableState().collectAsState()
    val query by viewModel.query

    val fabHeight = 72.dp
    val fabHeightPx = with(LocalDensity.current) { fabHeight.roundToPx().toFloat() }
    val fabOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {

                val delta = available.y
                val newOffset = fabOffsetHeightPx.value + delta
                fabOffsetHeightPx.value = newOffset.coerceIn(-fabHeightPx, 0f)

                return Offset.Zero
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(nestedScrollConnection),
            topBar = {
                TopAppBar(
                    modifier = Modifier.zIndex(-1F),
                    titleRes = R.string.app_name,
                    actionIcon = Icons.Rounded.Person,
                    actionIconContentDescription = stringResource(
                        id = R.string.about_page
                    ),
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    onActionClick = { onProfileClick() }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.offset { IntOffset(x = 0, y = -fabOffsetHeightPx.value.roundToInt()) },
                    onClick = { viewModel.setShowSettingsDialog(true) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_contact)
                    )
                }
            }
        ) { padding ->
            if (viewModel.shouldShowSettingsDialog) {
                SettingsDialog(
                    onDismiss = { viewModel.setShowSettingsDialog(false) }
                ) {
                    viewModel.handleEvent(ListViewEvent.Add(it))
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = {
                        viewModel.handleEvent(ListViewEvent.Filter(it))
                    }
                )
                Box(modifier = Modifier.weight(1f, fill = true)) {
                    if (viewState.value.isEmpty()) {
                        EmptyLayout()
                    } else {
                        LazyColumn(
                            modifier = Modifier.testTag(stringResource(id = R.string.scroll))
                        ) {
                            items(viewState.value, key = { it.id }) { hero ->
                                ContactItem(
                                    item = hero,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .animateItemPlacement(tween(durationMillis = 100)),
                                    onRemoveClicked = {
                                        viewModel.handleEvent(ListViewEvent.Remove(it))
                                    },
                                    onItemClick = {
                                        onItemClicked(it)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}