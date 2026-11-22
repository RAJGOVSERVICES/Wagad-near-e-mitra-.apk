package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataRepository
import com.example.model.ServiceCategory
import com.example.model.ServiceItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToServices: () -> Unit,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToForms: () -> Unit,
    onNavigateToPrinting: () -> Unit,
    onNavigateToTracking: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToAdmin: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToServiceDetail: (String) -> Unit
) {
    val profile by AppDataRepository.businessProfile.collectAsState()
    val categories by AppDataRepository.categories.collectAsState()
    val services by AppDataRepository.services.collectAsState()
    var isHindi by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredServices = services.filter {
        it.nameEn.contains(searchQuery, true) || it.nameHi.contains(searchQuery, true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isHindi) "वागड नीर डिजिटल ई-मित्र" else profile.businessName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isHindi) "देचा, डूंगरपुर (राजस्थान)" else "Decha, Dungarpur, Rajasthan",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Language Switch Button
                    TextButton(onClick = { isHindi = !isHindi }) {
                        Text(text = if (isHindi) "English" else "हिंदी", fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onNavigateToAdmin, modifier = Modifier.testTag("admin_icon_btn")) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin Panel")
                    }
                    IconButton(onClick = onNavigateToProfile, modifier = Modifier.testTag("profile_icon_btn")) {
                        Icon(Icons.Default.Person, contentDescription = "Customer Profile")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text(if (isHindi) "होम" else "Home") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Forms") },
                    label = { Text(if (isHindi) "फॉर्म्स" else "Forms") },
                    selected = false,
                    onClick = onNavigateToForms
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Print, contentDescription = "Printing") },
                    label = { Text(if (isHindi) "प्रिंटिंग" else "Print Anywhere") },
                    selected = false,
                    onClick = onNavigateToPrinting
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.TrackChanges, contentDescription = "Track") },
                    label = { Text(if (isHindi) "ट्रैक" else "Track") },
                    selected = false,
                    onClick = onNavigateToTracking
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Banner Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                            Text(
                                text = if (isHindi) "अधिकृत ई-मित्र सेवा केंद्र" else "Authorized Digital Seva Center",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = if (isHindi) "डिजिटल सेवा – अब आपके हाथ में" else profile.tagline,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = if (isHindi)
                                "आधार, जन आधार, जाति/आय प्रमाण पत्र, सरकारी फॉर्म, और कहीं से भी दस्तावेज़ प्रिंटिंग की सुविधा।"
                            else
                                "Aadhaar, Jan Aadhaar, Certificates, Gov Jobs, and Anywhere Document Printing Services.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = onNavigateToPrinting,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier.testTag("banner_print_btn")
                            ) {
                                Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(if (isHindi) "ऑनलाइन प्रिंट करें" else "Order Print Now")
                            }
                            OutlinedButton(
                                onClick = onNavigateToServices,
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                            ) {
                                Text(if (isHindi) "सभी सेवाएं" else "All Services")
                            }
                        }
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_services_input"),
                    placeholder = { Text(if (isHindi) "सरकारी सेवाएं या फॉर्म खोजें..." else "Search services, certificates, forms...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = if (searchQuery.isNotEmpty()) {
                        { IconButton(onClick = { searchQuery = "" }) { Icon(Icons.Default.Clear, contentDescription = null) } }
                    } else null,
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // If searching, show search results
            if (searchQuery.isNotEmpty()) {
                item {
                    Text(
                        text = if (isHindi) "खोज परिणाम (${filteredServices.size})" else "Search Results (${filteredServices.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                items(filteredServices) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToServiceDetail(service.id) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(if (isHindi) service.nameHi else service.nameEn, fontWeight = FontWeight.Bold) },
                            supportingContent = { Text(service.description) },
                            trailingContent = {
                                Text(
                                    "₹${service.serviceFee.toInt()}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            leadingContent = {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        )
                    }
                }
            } else {
                // Announcements Banner
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(Icons.Default.Campaign, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Column {
                                Text(
                                    text = if (isHindi) "महत्वपूर्ण सूचना / Announcements" else "Notice Board / Announcements",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isHindi)
                                        "जन आधार ई-केवाईसी और नई पेंशन सत्यापन अनिवार्य है। संपर्क करें: 9414300000"
                                    else
                                        "Jan Aadhaar e-KYC & Social Security Pension verification is active at Decha Center.",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                // Service Categories Grid
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "सेवा श्रेणियां (Service Categories)" else "Digital Service Categories",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = onNavigateToServices) {
                            Text(if (isHindi) "सभी देखें" else "View All")
                        }
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        categories.chunked(2).forEach { rowCategories ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowCategories.forEach { category ->
                                    Card(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable { onNavigateToCategory(category.id) },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Surface(
                                                shape = CircleShape,
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Icon(
                                                        imageVector = when (category.icon) {
                                                            "fingerprint" -> Icons.Default.Fingerprint
                                                            "family" -> Icons.Default.Groups
                                                            "badge" -> Icons.Default.Badge
                                                            "receipt_long" -> Icons.Default.ReceiptLong
                                                            "home" -> Icons.Default.Home
                                                            "restaurant" -> Icons.Default.Restaurant
                                                            "elderly" -> Icons.Default.Elderly
                                                            "school" -> Icons.Default.School
                                                            "work" -> Icons.Default.Work
                                                            "verified" -> Icons.Default.Verified
                                                            "agriculture" -> Icons.Default.Agriculture
                                                            "menu_book" -> Icons.Default.MenuBook
                                                            else -> Icons.Default.Print
                                                        },
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            }
                                            Text(
                                                text = if (isHindi) category.nameHi else category.nameEn,
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = category.description,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 2
                                            )
                                        }
                                    }
                                }
                                if (rowCategories.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // Quick Actions Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = if (isHindi) "त्वरित सेवाएं (Quick Services)" else "Popular Quick Services",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                QuickActionButton(
                                    icon = Icons.Default.Print,
                                    label = if (isHindi) "कहीं से भी प्रिंट" else "Print Anywhere",
                                    onClick = onNavigateToPrinting,
                                    modifier = Modifier.testTag("quick_print_btn")
                                )
                                QuickActionButton(
                                    icon = Icons.Default.Download,
                                    label = if (isHindi) "फॉर्म डाउनलोड" else "Download Forms",
                                    onClick = onNavigateToForms,
                                    modifier = Modifier.testTag("quick_forms_btn")
                                )
                                QuickActionButton(
                                    icon = Icons.Default.Info,
                                    label = if (isHindi) "हमारे बारे में" else "About Center",
                                    onClick = onNavigateToAbout,
                                    modifier = Modifier.testTag("quick_about_btn")
                                )
                            }
                        }
                    }
                }

                // Official Disclaimer Card
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = if (isHindi) "आधिकारिक सूचना / Disclaimer" else "Official Disclaimer",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Wagad Neer Digital eMitra Seva Kendra is an independent digital service center. It is not an official Rajasthan Government website or government department.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(onClick = onClick).padding(8.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(50.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}
