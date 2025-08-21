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
            val persona = if (personaId == "new") null else {
                // TODO: Get persona from repository
                null
            }
            PersonaEditScreen(
                persona = persona,
                onSave = { newPersona ->
                    // TODO: Save persona to repository
                    navController.popBackStack()
                },
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
                title = "アプリについて",
                content = """
                    wantEN - AIチャットアプリ
                    
                    バージョン: 1.0.0
                    AIモデル: Gemini 2.5 Flash Lite
                    
                    このアプリは、Google Gemini AIを使用した
                    パーソナライズされたチャット体験を提供します。
                    
                    特徴:
                    • カスタマイズ可能なAIペルソナ
                    • 自然な会話体験
                    • ローカルデータ保存
                    • プライバシー重視
                    
                    開発者: wantEN Team
                    ライセンス: MIT License
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Privacy.route) {
            InfoScreen(
                title = "プライバシーポリシー",
                content = """
                    プライバシーポリシー
                    
                    最終更新日: 2024年8月21日
                    
                    1. 情報収集
                    このアプリは、以下の情報を収集します：
                    • チャット履歴（ローカル保存のみ）
                    • ペルソナ設定（ローカル保存のみ）
                    
                    2. 情報の使用
                    収集した情報は以下の目的でのみ使用されます：
                    • チャット機能の提供
                    • ペルソナのカスタマイズ
                    • アプリの改善
                    
                    3. 情報の共有
                    あなたの個人情報を第三者と共有することはありません。
                    チャット内容はGoogle Gemini APIに送信されますが、
                    保存されることはありません。
                    
                    4. データの保存
                    すべてのデータはデバイス内にローカル保存されます。
                    クラウドへの自動アップロードは行われません。
                    
                    5. お問い合わせ
                    プライバシーに関するご質問は、
                    開発者までお問い合わせください。
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Terms.route) {
            InfoScreen(
                title = "利用規約",
                content = """
                    利用規約
                    
                    最終更新日: 2024年8月21日
                    
                    1. 利用条件
                    このアプリの利用により、以下の条件に同意したものとみなされます。
                    
                    2. 利用制限
                    以下の行為は禁止されています：
                    • 違法な内容の投稿
                    • 他者への誹謗中傷
                    • アプリの改変・逆コンパイル
                    • 商用利用（許可された場合を除く）
                    
                    3. 免責事項
                    開発者は、アプリの利用により生じた
                    いかなる損害についても責任を負いません。
                    
                    4. サービスの変更・終了
                    開発者は、事前の通知なく
                    サービスの内容を変更または終了する場合があります。
                    
                    5. 準拠法
                    本規約は日本法に準拠して解釈されます。
                    
                    6. 規約の変更
                    本規約は、事前の通知なく
                    変更される場合があります。
                """.trimIndent(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
