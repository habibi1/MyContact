package com.habibi.mycontact

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.habibi.mycontact.ui.theme.MyContactTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListContactInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: MainViewModel = MainViewModel()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyContactTheme {
                ListContact(viewModel = viewModel, onProfileClick = {}, onItemClicked = {})
            }
        }
    }

    @Test
    fun check_all_initial_components_is_showing() {
        composeTestRule.apply {
            onNodeWithText("Cari").assertExists()
            onNodeWithText("Daftar Kontak").assertExists()
            onNodeWithContentDescription("about_page").assertExists()
            onNodeWithContentDescription("Tambah Kontak").assertExists()
            onNodeWithTag("scroll").assertExists()
        }
    }

    @Test
    fun check_dialog_add_contact_is_showing() {
        composeTestRule.apply {
            onNodeWithContentDescription("Tambah Kontak").assertExists().performClick()
            onNodeWithText("Nama").assertExists()
            onNodeWithText("Telepon").assertExists()
            onNodeWithText("Email").assertExists()
            onNodeWithText("URL Foto").assertExists()
            onNodeWithText("Submit").assertExists()
        }
    }

    @Test
    fun check_feature_add_contact_is_working_well() {
        composeTestRule.apply {
            onNodeWithContentDescription("Tambah Kontak").assertExists().performClick()
            onNodeWithText("Nama").performTextInput("Habibi")
            onNodeWithText("Telepon").performTextInput("08211234567890")
            onNodeWithText("Email").performTextInput("email.email@email.com")
            onNodeWithText("URL Foto").performTextInput("www.google.com")
            onNodeWithText("Submit").performClick()

            onNodeWithTag("scroll").performScrollToIndex(15)
            onNodeWithText("Habibi").assertExists()
        }
    }

    @Test
    fun when_search_empty_list_then_show_empty() {
        composeTestRule.apply {
            onNodeWithText("Cari").performTextInput("qwerty")
            onNodeWithText("Kosong").assertExists()
        }
    }

    @Test
    fun check_feature_delete_contact_is_working_well() {
        composeTestRule.apply {
            onNodeWithText("Cari").performTextInput("Abdul Muis")
            onNodeWithContentDescription("Tombol Hapus").performClick()
            onNodeWithText("Kosong").assertExists()
        }
    }

    @Test
    fun when_scroll_then_fab_hidden() {
        composeTestRule.apply {
            onNodeWithTag("scroll").performScrollToIndex(14)
            onNodeWithText("Tambah Kontak").assertDoesNotExist()
        }
    }
}