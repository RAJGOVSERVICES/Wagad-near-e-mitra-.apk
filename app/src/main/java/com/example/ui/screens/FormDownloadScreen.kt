package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppDataRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDownloadScreen(
    onBack: () -> Unit
) {
    val forms by AppDataRepository.forms.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var downloadedFormTitle by remember { mutableStateOf<String?>(null) }

    val filteredForms = forms.filter {
        it.title.contains(searchQuery, true) || it.category.contains(searchQuery, true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Government & Service Forms") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("forms_back_btn")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth().testTag("search_forms_input"),
                placeholder = { Text("Search forms by title or category...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredForms) { form ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(form.title, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("${form.category} • Size: ${form.fileSize}\n${form.description}") },
                            trailingContent = {
                                IconButton(
                                    onClick = { downloadedFormTitle = form.title },
                                    modifier = Modifier.testTag("download_form_btn_${form.id}")
                                ) {
                                    Icon(Icons.Default.Download, contentDescription = "Download", tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        )
                    }
                }
            }
        }

        if (downloadedFormTitle != null) {
            AlertDialog(
                onDismissRequest = { downloadedFormTitle = null },
                title = { Text("Form Downloaded") },
                text = { Text("'$downloadedFormTitle' has been downloaded successfully to your device downloads folder.") },
                confirmButton = {
                    Button(onClick = { downloadedFormTitle = null }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
