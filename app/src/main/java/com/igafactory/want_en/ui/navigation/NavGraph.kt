package com.igafactory.want_en.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.igafactory.want_en.ui.screen.*

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.PersonaList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.PersonaList.route) {
            PersonaListScreen(
                onNavigateToChat = { personaId ->
                    navController.navigate(Screen.Chat.createRoute(personaId))
                },
                onNavigateToPersonaEdit = { personaId ->
                    navController.navigate(Screen.PersonaEdit.createRoute(personaId))
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("personaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val personaId = backStackEntry.arguments?.getString("personaId") ?: ""
            ChatScreen(
                personaId = personaId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.PersonaEdit.route,
            arguments = listOf(
                navArgument("personaId") { 
                    type = NavType.StringType
                    nullable = true
                    defaultValue = "new"
                }
            )
        ) { backStackEntry ->
            val personaId = backStackEntry.arguments?.getString("personaId")
            PersonaEditScreen(
                personaId = if (personaId == "new") null else personaId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToAbout = {
                    navController.navigate(Screen.About.route)
                },
                onNavigateToPrivacy = {
                    navController.navigate(Screen.Privacy.route)
                },
                onNavigateToTerms = {
                    navController.navigate(Screen.Terms.route)
                },
                onClearAllData = {
                    // TODO: Clear all data
                }
            )
        }
        
        composable(Screen.About.route) {
            InfoScreen(
                title = "About",
                content = """
                    wantEN - AI Chat App
                    
                    Version: 1.0.0
                    AI Model: Gemini 2.5 Flash Lite
                    
                    This app provides a personalized chat experience
                    using Google Gemini AI.
                    
                    Features:
                    • Customizable AI personas
                    • Natural conversation experience
                    • Local data storage
                    • Privacy-focused design
                    
                    Developer: wantEN Team
                    License: MIT License
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Privacy.route) {
            InfoScreen(
                title = "Privacy Policy",
                content = """
                    Privacy Policy
                    
                    Last Updated: August 21, 2024
                    
                    1. Information Collection
                    This app collects the following information:
                    • Chat history (local storage only)
                    • Persona settings (local storage only)
                    
                    2. Use of Information
                    Collected information is used only for:
                    • Providing chat functionality
                    • Persona customization
                    • App improvements
                    
                    3. Information Sharing
                    We do not share your personal information with third parties.
                    Chat content is sent to Google Gemini API but is not stored.
                    
                    4. Data Storage
                    All data is stored locally on your device.
                    No automatic upload to cloud services occurs.
                    
                    5. Contact
                    For privacy-related questions, please contact the developer.
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Terms.route) {
            InfoScreen(
                title = "Terms of Service",
                content = """
                    Terms of Service
                    
                    Last Updated: August 21, 2024
                    
                    1. Terms of Use
                    By using this app, you agree to the following terms.
                    
                    2. Usage Restrictions
                    The following activities are prohibited:
                    • Posting illegal content
                    • Defaming others
                    • Modifying or reverse engineering the app
                    • Commercial use (unless permitted)
                    
                    3. Disclaimer
                    The developer is not responsible for any damages
                    arising from the use of this app.
                    
                    4. Service Changes
                    The developer may change or terminate services
                    without prior notice.
                    
                    5. Governing Law
                    These terms are governed by Japanese law.
                    
                    6. Changes to Terms
                    These terms may be changed without prior notice.
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
