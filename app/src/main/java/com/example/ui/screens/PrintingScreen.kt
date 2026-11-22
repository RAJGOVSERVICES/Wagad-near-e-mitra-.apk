package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataRepository
import com.example.model.PrintOrder
import com.example.model.PrintOrderStatus
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintingScreen(
    onBack: () -> Unit
) {
    val pricing by AppDataRepository.printPricing.collectAsState()

    var customerName by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    var pageCount by remember { mutableStateOf("5") }
    var paperSize by remember { mutableStateOf("A4") } // A4 or A3
    var colorMode by remember { mutableStateOf("B&W") } // B&W or Colour
    var sides by remember { mutableStateOf("Single") } // Single or Double
    var copies by remember { mutableStateOf("1") }
    var deliveryType by remember { mutableStateOf("Pickup at Decha Center") } // Pickup or Home Delivery
    var isOrderPlaced by remember { mutableStateOf(false) }

    val pages = pageCount.toIntOrNull() ?: 1
    val numCopies = copies.toIntOrNull() ?: 1

    val ratePerPage = when {
        paperSize == "A3" && colorMode == "Colour" -> pricing.a3ColorPerPage
        paperSize == "A3" && colorMode == "B&W" -> pricing.a3BwPerPage
        paperSize == "A4" && colorMode == "Colour" -> pricing.a4ColorPerPage
        else -> pricing.a4BwPerPage
    }

    val printingCost = pages * ratePerPage * numCopies
    val deliveryCost = if (deliveryType == "Home Delivery") pricing.deliveryCharge else 0.0
    val totalAmount = printingCost + deliveryCost

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Anywhere Document Printing System") },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("printing_back_btn")) {
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
        if (isOrderPlaced) {
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
                Text("Print Order Placed Successfully!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Amount: ₹${totalAmount.toInt()} (${deliveryType})\nOrder ID: PRT-${System.currentTimeMillis().toString().takeLast(5)}", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
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
                // File Upload Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.UploadFile, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Upload Documents (PDF, JPG, PNG)", fontWeight = FontWeight.Bold)
                        }
                        Button(onClick = { /* File selection simulation */ }, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Default.Print, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Select PDF / Image File from Device")
                        }
                        Text("Selected File: Sample_Document.pdf (Detected: ~5 Pages)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                }

                // Print Options
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Print Settings & Options", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = pageCount,
                            onValueChange = { pageCount = it },
                            label = { Text("Number of Pages") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Paper Size selection
                        Text("Paper Size:", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("A4", "A3").forEach { size ->
                                FilterChip(
                                    selected = paperSize == size,
                                    onClick = { paperSize = size },
                                    label = { Text(size) }
                                )
                            }
                        }

                        // Color Mode selection
                        Text("Color Mode:", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("B&W", "Colour").forEach { mode ->
                                FilterChip(
                                    selected = colorMode == mode,
                                    onClick = { colorMode = mode },
                                    label = { Text(mode) }
                                )
                            }
                        }

                        // Sides
                        Text("Sides:", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Single", "Double").forEach { s ->
                                FilterChip(
                                    selected = sides == s,
                                    onClick = { sides = s },
                                    label = { Text(s) }
                                )
                            }
                        }

                        OutlinedTextField(
                            value = copies,
                            onValueChange = { copies = it },
                            label = { Text("Number of Copies") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                // Customer & Delivery Details
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Delivery & Contact Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = customerName,
                            onValueChange = { customerName = it },
                            label = { Text("Full Name *") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = mobile,
                            onValueChange = { mobile = it },
                            label = { Text("Mobile Number *") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )

                        Text("Delivery Mode:", fontWeight = FontWeight.SemiBold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("Pickup at Decha Center", "Home Delivery").forEach { mode ->
                                FilterChip(
                                    selected = deliveryType == mode,
                                    onClick = { deliveryType = mode },
                                    label = { Text(mode) }
                                )
                            }
                        }

                        if (deliveryType == "Home Delivery") {
                            OutlinedTextField(
                                value = address,
                                onValueChange = { address = it },
                                label = { Text("Delivery Address *") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )
                            OutlinedTextField(
                                value = pinCode,
                                onValueChange = { pinCode = it },
                                label = { Text("PIN Code (e.g. 314401) *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }
                }

                // Price Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Price Calculation", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Printing (${pages} pages x ${numCopies} copies):")
                            Text("₹${printingCost.toInt()}")
                        }
                        if (deliveryType == "Home Delivery") {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("Delivery Charge:")
                                Text("₹${deliveryCost.toInt()}")
                            }
                        }
                        Divider()
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Payable:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("₹${totalAmount.toInt()}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Button(
                    onClick = {
                        if (customerName.isNotBlank() && mobile.isNotBlank()) {
                            val order = PrintOrder(
                                id = "PRT-${System.currentTimeMillis().toString().takeLast(5)}",
                                customerName = customerName,
                                mobile = mobile,
                                address = if (address.isNotBlank()) address else "Decha Center Pickup",
                                pinCode = if (pinCode.isNotBlank()) pinCode else "314401",
                                fileNames = listOf("Sample_Document.pdf"),
                                pageCount = pages,
                                paperSize = paperSize,
                                colorMode = colorMode,
                                sides = sides,
                                copies = numCopies,
                                deliveryType = deliveryType,
                                totalAmount = totalAmount,
                                status = PrintOrderStatus.ORDER_RECEIVED,
                                orderDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            )
                            AppDataRepository.addPrintOrder(order)
                            isOrderPlaced = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp).testTag("submit_print_order_btn")
                ) {
                    Text("Confirm Print Order (₹${totalAmount.toInt()})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
