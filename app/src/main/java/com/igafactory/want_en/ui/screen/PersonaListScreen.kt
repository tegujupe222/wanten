package com.igafactory.want_en.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.igafactory.want_en.data.model.UserPersona
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaListScreen(
    personas: List<UserPersona> = emptyList(),
    onNavigateToChat: (String) -> Unit,
    onNavigateToPersonaEdit: (String?) -> Unit,
    onNavigateToSettings: () -> Unit,
    onDeletePersona: (String) -> Unit = {}
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var personaToDelete by remember { mutableStateOf<UserPersona?>(null) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ペルソナ") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "設定")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToPersonaEdit(null) }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "ペルソナを追加")
            }
        }
    ) { paddingValues ->
        if (personas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Filled.PersonAdd,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "ペルソナがありません",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "新しいペルソナを作成して\nチャットを始めましょう",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(personas) { persona ->
                    PersonaCard(
                        persona = persona,
                        onClick = { onNavigateToChat(persona.id) },
                        onEditClick = { onNavigateToPersonaEdit(persona.id) },
                        onDeleteClick = {
                            personaToDelete = persona
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }
    
    // 削除確認ダイアログ
    if (showDeleteDialog && personaToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("ペルソナを削除") },
            text = { Text("「${personaToDelete!!.displayName}」を削除しますか？この操作は取り消せません。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeletePersona(personaToDelete!!.id)
                        showDeleteDialog = false
                        personaToDelete = null
                    }
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showDeleteDialog = false
                    personaToDelete = null
                }) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaCard(
    persona: UserPersona,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // アバター
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(android.graphics.Color.parseColor(persona.customization.backgroundColor)),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = persona.displayName.firstOrNull()?.toString() ?: "?",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(android.graphics.Color.parseColor(persona.customization.textColor))
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // ペルソナ情報
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = persona.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = persona.relationship,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${persona.mood.emoji} ${persona.mood.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // アクションボタン
            Column {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "編集",
                        modifier = Modifier.size(16.dp)
                    )
                }
                
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "削除",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
