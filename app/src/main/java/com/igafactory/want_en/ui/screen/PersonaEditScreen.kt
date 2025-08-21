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
                title = { Text(if (persona == null) "New Persona" else "Edit Persona") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                        Icon(Icons.Filled.Save, contentDescription = "Save")
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
            // Basic Information Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Basic Information",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = relationship,
                        onValueChange = { relationship = it },
                        label = { Text("Relationship") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Personality & Speech Style Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Personality & Speech Style",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = personality,
                        onValueChange = { personality = it },
                        label = { Text("Personality (comma separated)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g., Kind, Cheerful, Reliable") }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = speechStyle,
                        onValueChange = { speechStyle = it },
                        label = { Text("Speech Style") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g., Polite and friendly tone" }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = catchphrases,
                        onValueChange = { catchphrases = it },
                        label = { Text("Catchphrases (comma separated)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g., I see, That's right, Indeed" }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Interests & Topics Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Interests & Topics",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    OutlinedTextField(
                        value = favoriteTopics,
                        onValueChange = { favoriteTopics = it },
                        label = { Text("Favorite Topics (comma separated)") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g., Music, Movies, Cooking, Travel" }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Current Mood",
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
            
            // Customization Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Customization",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Background Color")
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
                                Text("Change")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Text Color")
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
                                Text("Change")
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    OutlinedTextField(
                        value = fontSize,
                        onValueChange = { fontSize = it },
                        label = { Text("Font Size") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
        }
    }
    
    // Color Picker (Simple version)
    if (showColorPicker) {
        AlertDialog(
            onDismissRequest = { showColorPicker = false },
            title = { Text("Choose Color") },
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
                    Text("Cancel")
                }
            }
        )
    }
}
