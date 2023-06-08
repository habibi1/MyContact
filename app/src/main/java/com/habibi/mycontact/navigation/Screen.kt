package com.habibi.mycontact.navigation

sealed class Screen(val route: String) {
    object ListContact : Screen("listContact")
    object DetailContact : Screen("listContact/{id}") {
        fun createRoute(id: String) = "listContact/$id"
    }
    object AboutPage : Screen("aboutPage")
}