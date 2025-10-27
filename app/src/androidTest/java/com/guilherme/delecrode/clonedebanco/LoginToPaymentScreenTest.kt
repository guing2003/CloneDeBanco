package com.guilherme.delecrode.clonedebanco

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginToPaymentScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLoginFlow_andNavigationToPaymentScreen() {

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()

        composeTestRule.onNodeWithTag("email_field").performTextInput("teste@teste.com")
        composeTestRule.onNodeWithTag("password_field").performTextInput("Senha123")

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithTag("payment_screen_title").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("payment_screen_title").assertIsDisplayed()
    }

    @Test
    fun testBackNavigation_returnsToLoginScreen() {
        composeTestRule.onNodeWithTag("email_field").performTextInput("teste@teste.com")
        composeTestRule.onNodeWithTag("password_field").performTextInput("Senha123")
        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.waitUntil(timeoutMillis = 15000) {
            composeTestRule.onAllNodesWithTag("payment_screen_title").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithContentDescription(label = "Voltar").performClick()

        composeTestRule.waitUntil(timeoutMillis = 3000) {
            composeTestRule.onAllNodesWithText("Login").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Login").assertIsDisplayed()
    }
}
