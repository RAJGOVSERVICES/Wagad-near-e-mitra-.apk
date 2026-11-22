package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.AppDataRepository

@Composable
fun AdaptiveMainLayout(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    content: @Composable (isDesktop: Boolean) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val isDesktop = screenWidthDp >= 840

    val profile by AppDataRepository.businessProfile.collectAsState()

    if (isDesktop) {
        // Windows / Desktop Mode Layout with Permanent Sidebar
        Row(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            // Desktop Navigation Sidebar
            NavigationRail(
                modifier = Modifier.fillMaxInSize(),
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "WagadNeer",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Desktop Mode",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentRoute == "home",
                    onClick = { onNavigate("home") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Description, contentDescription = "Services") },
                    label = { Text("Services") },
                    selected = currentRoute.startsWith("services"),
                    onClick = { onNavigate("services/all") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Download, contentDescription = "Forms") },
                    label = { Text("Forms") },
                    selected = currentRoute == "forms",
                    onClick = { onNavigate("forms") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Print, contentDescription = "Print Anywhere") },
                    label = { Text("Print") },
                    selected = currentRoute == "printing",
                    onClick = { onNavigate("printing") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.TrackChanges, contentDescription = "Track") },
                    label = { Text("Track") },
                    selected = currentRoute == "tracking",
                    onClick = { onNavigate("tracking") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "About") },
                    label = { Text("About") },
                    selected = currentRoute == "about",
                    onClick = { onNavigate("about") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin") },
                    label = { Text("Admin") },
                    selected = currentRoute == "admin",
                    onClick = { onNavigate("admin") }
                )
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = currentRoute == "profile",
                    onClick = { onNavigate("profile") }
                )
            }

            // Main Desktop Content Area
            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                content(true)
            }
        }
    } else {
        // Android Mobile Mode Layout
        Box(modifier = Modifier.fillMaxSize()) {
            content(false)
        }
    }
}

private fun Modifier.fillMaxInSize(): Modifier = this.fillMaxHeight()
