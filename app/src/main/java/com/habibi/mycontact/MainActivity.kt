package com.habibi.mycontact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.habibi.mycontact.navigation.Screen
import com.habibi.mycontact.ui.theme.MyContactTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyContactTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.ListContact.route) {
                    composable(Screen.ListContact.route) {
                        ListContact(
                            viewModel = viewModel,
                            onProfileClick = {
                                navController.navigate(Screen.AboutPage.route)
                            },
                            onItemClicked = {
                                navController.navigate(Screen.DetailContact.createRoute(it))
                            }
                        )
                    }
                    composable(
                        route = Screen.DetailContact.route,
                        arguments = listOf(navArgument("id") {type = NavType.StringType})
                    ) {
                        val id = it.arguments?.getString("id").orEmpty()
                        DetailProfile(id = id, viewModel = viewModel)
                    }
                    composable(Screen.AboutPage.route) {
                        AboutPage()
                    }
                }
            }
        }
    }
}
