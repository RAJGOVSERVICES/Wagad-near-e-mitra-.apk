package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppDataRepository
import com.example.model.ApplicationRecord
import com.example.model.ApplicationStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    serviceId: String,
    onBack: () -> Unit,
    onNavigateToFormBuilder: (String) -> Unit
) {
    val services by AppDataRepository.services.collectAsState()
    val service = services.find { it.id == serviceId } ?: services.first()
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(service.nameEn) },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("detail_back_btn")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val record = ApplicationRecord(
                                id = "APP-${System.currentTimeMillis().toString().takeLast(4)}",
                                serviceName = service.nameEn,
                                customerName = "Dhaneshwar Patidar",
                                mobile = "+91 9414300000",
                                status = ApplicationStatus.RECEIVED,
                                submittedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                                notes = "Submitted via WagadNeer eMitra App",
                                fee = service.serviceFee
                            )
                            AppDataRepository.addApplication(record)
                            showSuccessDialog = true
                        },
                        modifier = Modifier.weight(1f).testTag("quick_apply_btn")
                    ) {
                        Text("Quick Submit Request")
                    }
                    Button(
                        onClick = { onNavigateToFormBuilder(service.id) },
                        modifier = Modifier.weight(1f).testTag("online_fill_btn")
                    ) {
                        Text("Fill Online Form")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = service.nameEn, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(text = service.nameHi, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = service.description, style = MaterialTheme.typography.bodyMedium)
                    Divider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Service Fee:", fontWeight = FontWeight.SemiBold)
                        Text("₹${service.serviceFee.toInt()}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    if (service.lastDate != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Last Date:", fontWeight = FontWeight.SemiBold)
                            Text(service.lastDate!!, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Required Documents
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Required Documents / आवश्यक दस्तावेज़", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    service.requiredDocuments.forEach { doc ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Text(doc, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Instructions & Application Process
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Instructions / निर्देश", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(service.instructions, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                title = { Text("Application Submitted Successfully") },
                text = { Text("Your application request for ${service.nameEn} has been submitted to Wagad Neer Digital eMitra Seva Kendra, Decha. We will review and process it shortly.") },
                confirmButton = {
                    Button(onClick = { showSuccessDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
