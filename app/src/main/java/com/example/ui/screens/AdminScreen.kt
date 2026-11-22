package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppDataRepository
import com.example.model.ApplicationStatus
import com.example.model.PrintOrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    onBack: () -> Unit
) {
    val profile by AppDataRepository.businessProfile.collectAsState()
    val pricing by AppDataRepository.printPricing.collectAsState()
    val applications by AppDataRepository.applications.collectAsState()
    val printOrders by AppDataRepository.printOrders.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }

    var businessNameInput by remember { mutableStateOf(profile.businessName) }
    var phoneInput by remember { mutableStateOf(profile.phone) }
    var a4BwInput by remember { mutableStateOf(pricing.a4BwPerPage.toString()) }
    var a4ColorInput by remember { mutableStateOf(pricing.a4ColorPerPage.toString()) }
    var isSaved by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard - WagadNeer eMitra") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("admin_back_btn")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    titleContentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            ScrollableTabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Overview") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Applications (${applications.size})") })
                Tab(selected = selectedTab == 2, onClick = { selectedTab = 2 }, text = { Text("Print Orders (${printOrders.size})") })
                Tab(selected = selectedTab == 3, onClick = { selectedTab = 3 }, text = { Text("Pricing & Settings") })
            }

            when (selectedTab) {
                0 -> {
                    // Overview Tab
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("Admin Control Center", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                                Text("Manage business settings, print rates, applications, and customer print orders in real-time.")
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Card(modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Applications", style = MaterialTheme.typography.bodyMedium)
                                    Text("${applications.size}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                            Card(modifier = Modifier.weight(1f)) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Print Orders", style = MaterialTheme.typography.bodyMedium)
                                    Text("${printOrders.size}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Applications Tab
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(applications) { app ->
                            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(app.id, fontWeight = FontWeight.Bold)
                                        Text(app.status.name, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                    }
                                    Text(app.serviceName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Text("Customer: ${app.customerName} (${app.mobile})")
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(onClick = { AppDataRepository.updateApplicationStatus(app.id, ApplicationStatus.PROCESSING) }) {
                                            Text("Set Processing")
                                        }
                                        Button(onClick = { AppDataRepository.updateApplicationStatus(app.id, ApplicationStatus.COMPLETED) }) {
                                            Text("Set Completed")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Print Orders Tab
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(printOrders) { order ->
                            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(order.id, fontWeight = FontWeight.Bold)
                                        Text(order.status.name, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                                    }
                                    Text("Files: ${order.fileNames.joinToString()} (${order.pageCount} pages, ${order.paperSize}, ${order.colorMode})")
                                    Text("Customer: ${order.customerName} (${order.mobile}) • Total: ₹${order.totalAmount.toInt()}")
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(onClick = { AppDataRepository.updatePrintOrderStatus(order.id, PrintOrderStatus.PRINTING_IN_PROGRESS) }) {
                                            Text("Print")
                                        }
                                        Button(onClick = { AppDataRepository.updatePrintOrderStatus(order.id, PrintOrderStatus.READY_FOR_PICKUP) }) {
                                            Text("Ready")
                                        }
                                        Button(onClick = { AppDataRepository.updatePrintOrderStatus(order.id, PrintOrderStatus.DELIVERED) }) {
                                            Text("Delivered")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                3 -> {
                    // Pricing & Business Settings
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("Edit Business Profile", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                OutlinedTextField(
                                    value = businessNameInput,
                                    onValueChange = { businessNameInput = it },
                                    label = { Text("Business Name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                OutlinedTextField(
                                    value = phoneInput,
                                    onValueChange = { phoneInput = it },
                                    label = { Text("Contact Phone") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }

                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text("Edit Print Pricing (₹)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                OutlinedTextField(
                                    value = a4BwInput,
                                    onValueChange = { a4BwInput = it },
                                    label = { Text("A4 B&W Per Page Rate") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                OutlinedTextField(
                                    value = a4ColorInput,
                                    onValueChange = { a4ColorInput = it },
                                    label = { Text("A4 Colour Per Page Rate") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )

                                Button(
                                    onClick = {
                                        val newProf = profile.copy(businessName = businessNameInput, phone = phoneInput)
                                        AppDataRepository.updateBusinessProfile(newProf)

                                        val newPr = pricing.copy(
                                            a4BwPerPage = a4BwInput.toDoubleOrNull() ?: 2.0,
                                            a4ColorPerPage = a4ColorInput.toDoubleOrNull() ?: 10.0
                                        )
                                        AppDataRepository.updatePrintPricing(newPr)
                                        isSaved = true
                                    },
                                    modifier = Modifier.fillMaxWidth().testTag("save_admin_settings_btn")
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Save Admin Settings")
                                }

                                if (isSaved) {
                                    Text("Settings updated successfully!", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
