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
class AboutPageInstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            MyContactTheme {
                AboutPage()
            }
        }
    }

    @Test
    fun check_all_components_is_showing() {
        composeTestRule.apply {
            onNodeWithText("Nama").assertExists()
            onNodeWithText("Habibi").assertExists()
            onNodeWithText("Email").assertExists()
            onNodeWithText("habibi.ilyas@gmail.com").assertExists()
            onNodeWithContentDescription("Foto Profil").assertExists()
        }
    }
}