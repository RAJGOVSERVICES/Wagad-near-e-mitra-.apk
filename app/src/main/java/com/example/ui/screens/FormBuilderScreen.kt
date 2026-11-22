package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataRepository
import com.example.model.ApplicationRecord
import com.example.model.ApplicationStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBuilderScreen(
    serviceId: String,
    onBack: () -> Unit
) {
    val services by AppDataRepository.services.collectAsState()
    val service = services.find { it.id == serviceId } ?: services.first()

    var fullName by remember { mutableStateOf("") }
    var fathersName by remember { mutableStateOf("") }
    var mothersName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var address by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Online Form: ${service.nameEn}") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("form_builder_back_btn")) {
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
        if (isSubmitted) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(80.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Application Submitted Successfully!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Your form has been successfully sent to Wagad Neer eMitra Seva Kendra, Decha for processing. Reference ID: APP-${System.currentTimeMillis().toString().takeLast(6)}", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                    Text("Back to Home")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Fill all mandatory details for ${service.nameEn}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name (पूरा नाम) *") },
                    modifier = Modifier.fillMaxWidth().testTag("input_full_name"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = fathersName,
                    onValueChange = { fathersName = it },
                    label = { Text("Father's Name (पिता का नाम) *") },
                    modifier = Modifier.fillMaxWidth().testTag("input_father_name"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = mothersName,
                    onValueChange = { mothersName = it },
                    label = { Text("Mother's Name (माता का नाम)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = dob,
                    onValueChange = { dob = it },
                    label = { Text("Date of Birth (DD-MM-YYYY) *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = mobile,
                    onValueChange = { mobile = it },
                    label = { Text("Mobile Number (मोबाइल नंबर) *") },
                    modifier = Modifier.fillMaxWidth().testTag("input_mobile"),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Full Address (पता - Decha, Dungarpur) *") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )

                // Document upload simulation
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.UploadFile, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Upload Supporting Documents (Aadhaar / Photo)", fontWeight = FontWeight.Bold)
                        }
                        Text("Supported formats: PDF, JPG, PNG (Max 5MB)", style = MaterialTheme.typography.bodySmall)
                        Button(onClick = { /* File picker simulation */ }, modifier = Modifier.fillMaxWidth()) {
                            Text("Select Document / Aadhaar Card")
                        }
                    }
                }

                Button(
                    onClick = {
                        if (fullName.isNotBlank() && mobile.isNotBlank()) {
                            val record = ApplicationRecord(
                                id = "APP-${System.currentTimeMillis().toString().takeLast(6)}",
                                serviceName = service.nameEn,
                                customerName = fullName,
                                mobile = mobile,
                                status = ApplicationStatus.RECEIVED,
                                submittedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                                notes = "Online form submission",
                                fee = service.serviceFee
                            )
                            AppDataRepository.addApplication(record)
                            isSubmitted = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("submit_form_btn")
                ) {
                    Text("Submit Application (₹${service.serviceFee.toInt()})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
