package com.igafactory.want_en.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.igafactory.want_en.ui.screen.ChatScreen
import com.igafactory.want_en.ui.screen.PersonaListScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PersonaList.route
    ) {
        composable(Screen.PersonaList.route) {
            PersonaListScreen(
                onPersonaClick = { personaId ->
                    navController.navigate(Screen.Chat.createRoute(personaId))
                },
                onAddPersona = {
                    // TODO: Navigate to add persona screen
                }
            )
        }
        
        composable(
            route = Screen.Chat.route,
            arguments = Screen.Chat.arguments
        ) { backStackEntry ->
            val personaId = backStackEntry.arguments?.getString("personaId")
            ChatScreen(
                personaId = personaId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
