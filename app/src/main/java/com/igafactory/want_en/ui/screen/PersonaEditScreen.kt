package com.igafactory.want_en.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.igafactory.want_en.data.model.PersonaMood
import com.igafactory.want_en.data.model.UserPersona

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonaEditScreen(
    persona: UserPersona? = null,
    onSave: (UserPersona) -> Unit,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf(persona?.name ?: "") }
    var relationship by remember { mutableStateOf(persona?.relationship ?: "") }
    var personality by remember { mutableStateOf(persona?.personality?.joinToString(", ") ?: "") }
    var speechStyle by remember { mutableStateOf(persona?.speechStyle ?: "") }
    var catchphrases by remember { mutableStateOf(persona?.catchphrases?.joinToString(", ") ?: "") }
    var favoriteTopics by remember { mutableStateOf(persona?.favoriteTopics?.joinToString(", ") ?: "") }
    var selectedMood by remember { mutableStateOf(persona?.mood ?: PersonaMood.NEUTRAL) }
    
    var backgroundColor by remember { mutableStateOf(persona?.customization?.backgroundColor ?: "#FFFFFF") }
    var textColor by remember { mutableStateOf(persona?.customization?.textColor ?: "#000000") }
    var fontSize by remember { mutableStateOf(persona?.customization?.fontSize?.toString() ?: "16") }
    
    var showColorPicker by remember { mutableStateOf(false) }
    var colorPickerType by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (persona == null) "新しいペルソナ" else "ペルソナを編集") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val newPersona = UserPersona(
                                id = persona?.id ?: "",
                                name = name,
                                relationship = relationship,
                                personality = personality.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                speechStyle = speechStyle,
                                catchphrases = catchphrases.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                favoriteTopics = favoriteTopics.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                                mood = selectedMood,
                                customization = persona?.customization?.copy(
                                    backgroundColor = backgroundColor,
                                    textColor = textColor,
                                    fontSize = fontSize.toIntOrNull() ?: 16
                                ) ?: persona?.customization ?: com.igafactory.want_en.data.model.PersonaCustomization(
                                    backgroundColor = backgroundColor,
                                    textColor = textColor,
                                    fontSize = fontSize.toIntOrNull() ?: 16
                                )
                            )
                            onSave(newPersona)
                        },
                        enabled = name.isNotBlank() && relationship.isNotBlank()
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = "保存")
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
                .padding(16.dp)
        ) {
            // 基本情報セクション
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "基本情報",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("名前") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = relationship,
                        onValueChange = { relationship = it },
                        label = { Text("関係性") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 性格・話し方セクション
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "性格・話し方",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = personality,
                        onValueChange = { personality = it },
                        label = { Text("性格（カンマ区切り）") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("例: 親切, 明るい, 頼りになる") }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = speechStyle,
                        onValueChange = { speechStyle = it },
                        label = { Text("話し方") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("例: 丁寧で親しみやすい口調" }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = catchphrases,
                        onValueChange = { catchphrases = it },
                        label = { Text("口癖（カンマ区切り）") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("例: なるほど, そうですね, 確かに" }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 興味・話題セクション
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "興味・話題",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = favoriteTopics,
                        onValueChange = { favoriteTopics = it },
                        label = { Text("好きな話題（カンマ区切り）") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("例: 音楽, 映画, 料理, 旅行" }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "現在の気分",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(PersonaMood.values()) { mood ->
                            FilterChip(
                                selected = selectedMood == mood,
                                onClick = { selectedMood = mood },
                                label = { Text("${mood.emoji} ${mood.displayName}") }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // カスタマイズセクション
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "カスタマイズ",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("背景色")
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(android.graphics.Color.parseColor(backgroundColor)))
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(
                                onClick = {
                                    colorPickerType = "background"
                                    showColorPicker = true
                                }
                            ) {
                                Text("変更")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("文字色")
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color(android.graphics.Color.parseColor(textColor)))
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(
                                onClick = {
                                    colorPickerType = "text"
                                    showColorPicker = true
                                }
                            ) {
                                Text("変更")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = fontSize,
                        onValueChange = { fontSize = it },
                        label = { Text("フォントサイズ") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
        }
    }
    
    // カラーピッカー（簡易版）
    if (showColorPicker) {
        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            title = { Text("色を選択") },
            text = {
                Column {
                    val colors = listOf("#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#FF00FF", "#00FFFF", "#000000", "#FFFFFF")
                    LazyRow {
                        items(colors) { color ->
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(Color(android.graphics.Color.parseColor(color)))
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                TextButton(
                                    onClick = {
                                        when (colorPickerType) {
                                            "background" -> backgroundColor = color
                                            "text" -> textColor = color
                                        }
                                        showColorPicker = false
                                    }
                                ) {
                                    Text("")
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showColorPicker = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
}
