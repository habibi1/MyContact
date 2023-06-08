package com.habibi.mycontact

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.habibi.mycontact.ui.theme.MyContactTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailProfileInstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: MainViewModel = MainViewModel()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyContactTheme {
                DetailProfile(
                    id = "0",
                    viewModel = viewModel
                )
            }
        }
    }

    @Test
    fun check_all_components_is_showing() {
        composeTestRule.apply {
            onNodeWithText("Nama").assertExists().assertIsNotEnabled()
            onNodeWithText("Abdul Muis").assertExists()
            onNodeWithText("Telepon").assertExists().assertIsNotEnabled()
            onNodeWithText("082100000001").assertExists()
            onNodeWithText("Email").assertExists().assertIsNotEnabled()
            onNodeWithText("email.1@gmail.com").assertExists()
            onNodeWithContentDescription("Foto Profil").assertExists()
        }
    }
}