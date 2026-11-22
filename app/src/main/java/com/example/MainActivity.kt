package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.ui.components.AdaptiveMainLayout
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

                    AdaptiveMainLayout(
                        currentRoute = currentRoute,
                        onNavigate = { route -> navController.navigate(route) }
                    ) { isDesktop ->
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(
                                    onNavigateToServices = { navController.navigate("services/all") },
                                    onNavigateToCategory = { catId -> navController.navigate("services/$catId") },
                                    onNavigateToForms = { navController.navigate("forms") },
                                    onNavigateToPrinting = { navController.navigate("printing") },
                                    onNavigateToTracking = { navController.navigate("tracking") },
                                    onNavigateToAbout = { navController.navigate("about") },
                                    onNavigateToAdmin = { navController.navigate("admin") },
                                    onNavigateToProfile = { navController.navigate("profile") },
                                    onNavigateToServiceDetail = { serviceId -> navController.navigate("service_detail/$serviceId") }
                                )
                            }

                            composable(
                                route = "services/{categoryId}",
                                arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val categoryId = backStackEntry.arguments?.getString("categoryId")
                                ServicesScreen(
                                    initialCategoryId = categoryId,
                                    onBack = { navController.popBackStack() },
                                    onNavigateToDetail = { serviceId -> navController.navigate("service_detail/$serviceId") }
                                )
                            }

                            composable(
                                route = "service_detail/{serviceId}",
                                arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
                                ServiceDetailScreen(
                                    serviceId = serviceId,
                                    onBack = { navController.popBackStack() },
                                    onNavigateToFormBuilder = { sId -> navController.navigate("form_builder/$sId") }
                                )
                            }

                            composable("forms") {
                                FormDownloadScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable(
                                route = "form_builder/{serviceId}",
                                arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
                                FormBuilderScreen(
                                    serviceId = serviceId,
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable("printing") {
                                PrintingScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable("tracking") {
                                TrackingScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable("about") {
                                AboutScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable("admin") {
                                AdminScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }

                            composable("profile") {
                                ProfileScreen(
                                    onBack = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
