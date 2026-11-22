package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppDataRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(
    initialCategoryId: String?,
    onBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    val categories by AppDataRepository.categories.collectAsState()
    val services by AppDataRepository.services.collectAsState()

    var selectedCategoryId by remember { mutableStateOf(initialCategoryId ?: "all") }

    val displayedServices = if (selectedCategoryId == "all") {
        services
    } else {
        services.filter { it.categoryId == selectedCategoryId }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Government & Digital Services") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("services_back_btn")) {
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
        ) {
            // Category filter chips
            ScrollableTabRow(
                selectedTabIndex = if (selectedCategoryId == "all") 0 else categories.indexOfFirst { it.id == selectedCategoryId }.coerceAtLeast(0) + 1,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedCategoryId == "all",
                    onClick = { selectedCategoryId = "all" },
                    text = { Text("All Services") }
                )
                categories.forEach { cat ->
                    Tab(
                        selected = selectedCategoryId == cat.id,
                        onClick = { selectedCategoryId = cat.id },
                        text = { Text(cat.nameEn) }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayedServices) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetail(service.id) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(service.nameEn, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text(service.description, maxLines = 2) },
                            trailingContent = {
                                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                                    Text(
                                        "₹${service.serviceFee.toInt()}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.ChevronRight, contentDescription = null)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
