package com.habibi.mycontact.ui.common

import com.habibi.mycontact.model.Contact

sealed class ListViewEvent {
    data class Add(val item: Contact) : ListViewEvent()
    data class Remove(val item: Contact) : ListViewEvent()
    data class Filter(val query: String) : ListViewEvent()
}