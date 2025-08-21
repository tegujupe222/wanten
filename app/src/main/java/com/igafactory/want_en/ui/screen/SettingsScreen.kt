package com.igafactory.want_en.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onClearAllData: () -> Unit
) {
    var showClearDataDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // アプリ情報セクション
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "アプリ情報",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("バージョン")
                        Text("1.0.0", style = MaterialTheme.typography.bodyMedium)
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("AIモデル")
                        Text("Gemini 2.5 Flash Lite", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            
            // データ管理セクション
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "データ管理",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    ListItem(
                        headlineContent = { Text("すべてのデータを削除") },
                        supportingContent = { Text("チャット履歴とペルソナを削除します") },
                        leadingContent = { Icon(Icons.Filled.Delete, contentDescription = null) },
                        modifier = Modifier.clickable { showClearDataDialog = true }
                    )
                    
                    ListItem(
                        headlineContent = { Text("アプリをリセット") },
                        supportingContent = { Text("アプリを初期状態に戻します") },
                        leadingContent = { Icon(Icons.Filled.Refresh, contentDescription = null) },
                        modifier = Modifier.clickable { showResetDialog = true }
                    )
                }
            }
            
            // サポートセクション
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "サポート",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    ListItem(
                        headlineContent = { Text("アプリについて") },
                        supportingContent = { Text("アプリの詳細情報") },
                        leadingContent = { Icon(Icons.Filled.Info, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToAbout() }
                    )
                    
                    ListItem(
                        headlineContent = { Text("プライバシーポリシー") },
                        supportingContent = { Text("プライバシーに関する情報") },
                        leadingContent = { Icon(Icons.Filled.PrivacyTip, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToPrivacy() }
                    )
                    
                    ListItem(
                        headlineContent = { Text("利用規約") },
                        supportingContent = { Text("利用規約の詳細") },
                        leadingContent = { Icon(Icons.Filled.Description, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToTerms() }
                    )
                }
            }
            
            // 開発者情報セクション
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "開発者情報",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("開発者")
                        Text("wantEN Team", style = MaterialTheme.typography.bodyMedium)
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("ライセンス")
                        Text("MIT License", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
    
    // データ削除確認ダイアログ
    if (showClearDataDialog) {
        AlertDialog(
            onDismissRequest = { showClearDataDialog = false },
            title = { Text("データを削除") },
            text = { Text("すべてのチャット履歴とペルソナを削除しますか？この操作は取り消せません。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllData()
                        showClearDataDialog = false
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearDataDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
    
    // リセット確認ダイアログ
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("アプリをリセット") },
            text = { Text("アプリを初期状態に戻しますか？すべてのデータが削除されます。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllData()
                        showResetDialog = false
                    }
                ) {
                    Text("リセット")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
}
