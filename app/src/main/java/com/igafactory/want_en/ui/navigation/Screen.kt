package com.igafactory.want_en.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object PersonaList : Screen("persona_list")
    object Chat : Screen("chat/{personaId}") {
        val arguments = listOf(
            navArgument("personaId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
        
        fun createRoute(personaId: String? = null): String {
            return if (personaId != null) {
                "chat/$personaId"
            } else {
                "chat/null"
            }
        }
    }
}
