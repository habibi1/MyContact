package com.habibi.mycontact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.habibi.mycontact.model.Contact
import com.habibi.mycontact.model.ContactsData
import com.habibi.mycontact.ui.common.ListViewEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private var list = ContactsData.contacts

    var shouldShowSettingsDialog by mutableStateOf(false)
        private set

    var query = mutableStateOf("")
        private set

    private val uiState = MutableStateFlow(emptyList<Contact>())

    fun consumableState() = uiState.asStateFlow()

    init {
        getList()
    }

    fun setShowSettingsDialog(shouldShow: Boolean) {
        shouldShowSettingsDialog = shouldShow
    }

    fun getDetailContact(id: String) : Contact? {
        return list.find { it.id == id }
    }

    fun handleEvent(event: ListViewEvent) {
        when (event) {
            is ListViewEvent.Add -> {
                val item = event.item.copy(id = list.size.toString())
                list = list.toMutableList().apply {
                    add(item)
                }.toList()
                filterList()
            }
            is ListViewEvent.Remove -> {
                list = list.toMutableList().apply {
                    remove(event.item)
                }.toList()
                filterList()
            }
            is ListViewEvent.Filter -> {
                query.value = event.query
                filterList()
            }
        }
    }

    private fun filterList() {
        val query = query.value
        if (query.isEmpty()) {
            uiState.value = list
        } else {
            val currentList = list.filter { it.name.contains(query, ignoreCase = true) }
            uiState.value = currentList
        }
    }

    private fun getList() {
        uiState.value = ContactsData.contacts
    }
}
