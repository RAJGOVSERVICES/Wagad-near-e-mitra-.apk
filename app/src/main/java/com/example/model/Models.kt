package com.example.model

data class BusinessProfile(
    val businessName: String = "Wagad Neer Digital eMitra Seva Kendra",
    val subLocation: String = "Decha, Tehsil Obri, District Dungarpur, Rajasthan - 314401",
    val fullAddress: String = "Kesarpura Road, Near Van Vibhag, Decha, Tehsil Obri, District Dungarpur, Rajasthan – 314401, India",
    val owners: List<String> = listOf("Dhaneshwar Bhai Patidar", "Piyush Patidar"),
    val developer: String = "SS Chouhan",
    val phone: String = "+91 9414300000",
    val whatsapp: String = "+91 9414300000",
    val email: String = "wagadneeremitra@gmail.com",
    val tagline: String = "Digital Seva – Ab Aapke Haath Mein",
    val openingHours: String = "Mon - Sat: 8:00 AM - 8:00 PM (Sunday Closed)"
)

data class ServiceCategory(
    val id: String,
    val nameEn: String,
    val nameHi: String,
    val icon: String,
    val description: String
)

data class ServiceItem(
    val id: String,
    val categoryId: String,
    val nameEn: String,
    val nameHi: String,
    val description: String,
    val requiredDocuments: List<String>,
    val serviceFee: Double,
    val officialUrl: String,
    val lastDate: String?,
    val instructions: String
)

data class FormItem(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val fileSize: String,
    val downloadUrl: String
)

enum class ApplicationStatus {
    RECEIVED,
    DOCUMENT_VERIFICATION,
    PROCESSING,
    COMPLETED,
    REJECTED
}

data class ApplicationRecord(
    val id: String,
    val serviceName: String,
    val customerName: String,
    val mobile: String,
    val status: ApplicationStatus,
    val submittedDate: String,
    val notes: String,
    val fee: Double
)

enum class PrintOrderStatus {
    ORDER_RECEIVED,
    PAYMENT_PENDING,
    PAYMENT_CONFIRMED,
    PRINTING_IN_PROGRESS,
    READY_FOR_PICKUP,
    DISPATCHED,
    DELIVERED,
    CANCELLED
}

data class PrintOrder(
    val id: String,
    val customerName: String,
    val mobile: String,
    val address: String,
    val pinCode: String,
    val fileNames: List<String>,
    val pageCount: Int,
    val paperSize: String, // A4, A3
    val colorMode: String, // Color, B&W
    val sides: String, // Single, Double
    val copies: Int,
    val deliveryType: String, // Pickup at Decha, Home Delivery
    val totalAmount: Double,
    val status: PrintOrderStatus,
    val orderDate: String
)

data class PrintPricing(
    var a4BwPerPage: Double = 2.0,
    var a4ColorPerPage: Double = 10.0,
    var a3BwPerPage: Double = 5.0,
    var a3ColorPerPage: Double = 20.0,
    var deliveryCharge: Double = 30.0,
    var expressCharge: Double = 20.0
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false
)

data class UserProfile(
    val name: String = "Guest Customer",
    val mobile: String = "",
    val email: String = "",
    val address: String = "",
    val isRegistered: Boolean = false
)
