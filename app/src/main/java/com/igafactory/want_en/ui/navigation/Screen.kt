package com.igafactory.want_en.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object PersonaList : Screen("persona_list")
    object Chat : Screen("chat/{personaId}") {
        fun createRoute(personaId: String) = "chat/$personaId"
    }
    object PersonaEdit : Screen("persona_edit/{personaId}") {
        fun createRoute(personaId: String? = null) = "persona_edit/${personaId ?: "new"}"
    }
    object Settings : Screen("settings")
    object About : Screen("about")
    object Privacy : Screen("privacy")
    object Terms : Screen("terms")
}
