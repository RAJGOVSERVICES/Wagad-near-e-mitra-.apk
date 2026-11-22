package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppDataRepository {
    private val _businessProfile = MutableStateFlow(BusinessProfile())
    val businessProfile: StateFlow<BusinessProfile> = _businessProfile.asStateFlow()

    private val _printPricing = MutableStateFlow(PrintPricing())
    val printPricing: StateFlow<PrintPricing> = _printPricing.asStateFlow()

    private val _categories = MutableStateFlow(
        listOf(
            ServiceCategory("cat_aadhaar", "Aadhaar Services", "आधार सेवा", "fingerprint", "Aadhaar update, PVC card print, demographic updates"),
            ServiceCategory("cat_jan_aadhaar", "Jan Aadhaar", "जन आधार", "family", "Jan Aadhaar card enrollment, member addition, e-KYC"),
            ServiceCategory("cat_caste", "Caste Certificate", "जाति प्रमाण पत्र", "badge", "OBC, SC, ST, EWS Caste certificates for Rajasthan"),
            ServiceCategory("cat_income", "Income Certificate", "आय प्रमाण पत्र", "receipt_long", "Income proof for scholarships and government schemes"),
            ServiceCategory("cat_domicile", "Domicile Certificate", "मूल निवास प्रमाण पत्र", "home", "Rajasthan Domicile / Mool Niwas certificate application"),
            ServiceCategory("cat_ration", "Ration Card", "राशन कार्ड", "restaurant", "New ration card, member addition, fair price shop change"),
            ServiceCategory("cat_pension", "Pension Services", "पेंशन सेवाएं", "elderly", "Old age, widow, and divyang pension verification & applications"),
            ServiceCategory("cat_scholarship", "Scholarship", "छात्रवृत्ति", "school", "Post-matric and pre-matric scholarship applications"),
            ServiceCategory("cat_jobs", "Government Job Forms", "सरकारी नौकरी फॉर्म", "work", "Rajasthan Police, REET, CET, SSC, UPSC online forms"),
            ServiceCategory("cat_schemes", "Rajasthan Schemes", "राजस्थान योजनाएं", "verified", "Chiranjeevi, Palanhar, and welfare scheme applications"),
            ServiceCategory("cat_farmer", "Farmer Services", "किसान सेवाएं", "agriculture", "PM Kisan e-KYC, KCC, land records"),
            ServiceCategory("cat_education", "Education & University", "शिक्षा सेवाएं", "menu_book", "University exam forms, re-evaluation, admit cards"),
            ServiceCategory("cat_printing", "Online Printing & Xerox", "ऑनलाइन प्रिंटिंग", "print", "Order document print from anywhere in India")
        )
    )
    val categories: StateFlow<List<ServiceCategory>> = _categories.asStateFlow()

    private val _services = MutableStateFlow(
        listOf(
            ServiceItem(
                "srv_aadhaar_pvc", "cat_aadhaar",
                "Aadhaar PVC Card Print", "आधार पीवीसी कार्ड प्रिंट",
                "Order secure, durable, rain-proof Aadhaar PVC card delivered to your doorstep or pickup at Decha center.",
                listOf("Aadhaar Number or Registered Mobile", "OTP Verification"),
                50.0, "https://uidai.gov.in", "2026-12-31",
                "Provide your 12-digit Aadhaar number. OTP will be sent to your registered mobile number for verification."
            ),
            ServiceItem(
                "srv_jan_aadhaar", "cat_jan_aadhaar",
                "Jan Aadhaar Card New / Update", "जन आधार कार्ड नया / संशोधन",
                "New family registration, member addition, name/DOB correction, address update in Rajasthan Jan Aadhaar.",
                listOf("Aadhaar Card of all members", "Bank Passbook", "Passport Photo", "Residential Proof"),
                40.0, "https://janbazaar.rajasthan.gov.in", null,
                "Required documents must be clear scanned copies in PDF or JPG format."
            ),
            ServiceItem(
                "srv_caste", "cat_caste",
                "Caste Certificate (OBC/SC/ST/EWS)", "जाति प्रमाण पत्र (ओबीसी/एससी/एसटी)",
                "Official Rajasthan Government caste certificate for reservation, employment, and education.",
                listOf("Aadhaar Card", "Jan Aadhaar", "Affidavit / Patwari Report", "Old Caste Proof (if any)"),
                60.0, "https://emitra.rajasthan.gov.in", null,
                "Must be verified by local Patwari or Sarpanch verification slip."
            ),
            ServiceItem(
                "srv_income", "cat_income",
                "Income Certificate (आय प्रमाण पत्र)", "आय प्रमाण पत्र",
                "Annual family income certificate required for scholarships, fee concessions, and government benefits.",
                listOf("Aadhaar Card", "Income Declaration Affidavit (Notary)", "Jan Aadhaar", "Ration Card"),
                60.0, "https://emitra.rajasthan.gov.in", null,
                "Income affidavit must be signed by 2 responsible government witnesses or Notary public."
            ),
            ServiceItem(
                "srv_domicile", "cat_domicile",
                "Domicile / Mool Niwas Certificate", "मूल निवास प्रमाण पत्र",
                "Proof of residence in Rajasthan state for domicile quota and benefits.",
                listOf("Aadhaar Card", "Ration Card / Electricity Bill", "School Transfer Certificate", "Jan Aadhaar"),
                60.0, "https://emitra.rajasthan.gov.in", null,
                "Applicant must be a permanent resident of Rajasthan."
            ),
            ServiceItem(
                "srv_pension", "cat_pension",
                "Social Security Pension (SPS)", "सामाजिक सुरक्षा पेंशन",
                "Old age, widow, and disabled pension verification, annual verification (Jeevan Praman), and new applications.",
                listOf("Aadhaar Card", "Jan Aadhaar", "Bank Passbook", "Age Proof / BPL Card"),
                30.0, "https://ssp.rajasthan.gov.in", null,
                "Biometric fingerprint verification required at Decha Center or via mobile."
            ),
            ServiceItem(
                "srv_jobs", "cat_jobs",
                "Rajasthan Govt Job Online Form", "सरकारी नौकरी ऑनलाइन फॉर्म",
                "Expert filling of Rajasthan CET, REET, Police Constable, VDO, Patwari, and SSC/UPSC exam applications.",
                listOf("10th & 12th Marksheet", "Graduation Degree (if applicable)", "Photo & Signature", "Category Certificate", "Aadhaar"),
                100.0, "https://rsmssb.rajasthan.gov.in", "2026-06-30",
                "Double-check all personal details before final submission to avoid rejection."
            )
        )
    )
    val services: StateFlow<List<ServiceItem>> = _services.asStateFlow()

    private val _forms = MutableStateFlow(
        listOf(
            FormItem("form_1", "Jan Aadhaar Enrollment Form", "Jan Aadhaar", "Official form for new Jan Aadhaar family registration", "1.2 MB", "https://example.com/janaadhaar.pdf"),
            FormItem("form_2", "Caste Certificate Application Form", "Certificates", "Rajasthan standard caste certificate application format", "850 KB", "https://example.com/caste.pdf"),
            FormItem("form_3", "Income Certificate Affidavit Form", "Certificates", "Self-declaration income form with notary witness columns", "620 KB", "https://example.com/income.pdf"),
            FormItem("form_4", "Domicile Certificate Form", "Certificates", "Mool niwas application form for Rajasthan residents", "900 KB", "https://example.com/domicile.pdf"),
            FormItem("form_5", "Old Age Pension Application Form", "Pension", "Social security pension application form", "1.1 MB", "https://example.com/pension.pdf"),
            FormItem("form_6", "Ration Card New Application", "Ration Card", "New ration card or name addition deletion form", "1.5 MB", "https://example.com/ration.pdf")
        )
    )
    val forms: StateFlow<List<FormItem>> = _forms.asStateFlow()

    private val _applications = MutableStateFlow(
        mutableListOf(
            ApplicationRecord("APP-1001", "Aadhaar PVC Card Print", "Ramesh Kumar Patidar", "+91 9414112233", ApplicationStatus.COMPLETED, "2026-11-15", "Printed and dispatched", 50.0),
            ApplicationRecord("APP-1002", "Caste Certificate", "Sunita Devi", "+91 9829554433", ApplicationStatus.PROCESSING, "2026-11-18", "Patwari verification pending", 60.0)
        )
    )
    val applications: StateFlow<List<ApplicationRecord>> = _applications.asStateFlow()

    private val _printOrders = MutableStateFlow(
        mutableListOf(
            PrintOrder("PRT-501", "Mahesh Patidar", "+91 9784112233", "Decha Main Bazaar, Dungarpur", "314401", listOf("Exam_Admit_Card.pdf"), 4, "A4", "Colour", "Single", 2, "Pickup at Decha Center", 90.0, PrintOrderStatus.READY_FOR_PICKUP, "2026-11-18"),
            PrintOrder("PRT-502", "Pankaj Menaria", "+91 9414998877", "Kesarpura Road, Decha", "314401", listOf("Project_Report.pdf"), 25, "A4", "B&W", "Double", 1, "Home Delivery", 80.0, PrintOrderStatus.PRINTING_IN_PROGRESS, "2026-11-19")
        )
    )
    val printOrders: StateFlow<List<PrintOrder>> = _printOrders.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem("notif_1", "Welcome to WagadNeer eMitra", "Welcome to official digital service center app of Decha, Dungarpur.", "2026-11-19 08:00 AM", false),
            NotificationItem("notif_2", "Print Order Ready", "Your print order PRT-501 is ready for pickup at Decha Center.", "2026-11-18 04:30 PM", false)
        )
    )
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _currentUser = MutableStateFlow(UserProfile())
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    fun updateBusinessProfile(newProfile: BusinessProfile) {
        _businessProfile.value = newProfile
    }

    fun updatePrintPricing(newPricing: PrintPricing) {
        _printPricing.value = newPricing
    }

    fun addApplication(record: ApplicationRecord) {
        val list = _applications.value.toMutableList()
        list.add(0, record)
        _applications.value = list
    }

    fun addPrintOrder(order: PrintOrder) {
        val list = _printOrders.value.toMutableList()
        list.add(0, order)
        _printOrders.value = list
    }

    fun updateApplicationStatus(id: String, status: ApplicationStatus) {
        _applications.value = _applications.value.map {
            if (it.id == id) it.copy(status = status) else it
        }.toMutableList()
    }

    fun updatePrintOrderStatus(id: String, status: PrintOrderStatus) {
        _printOrders.value = _printOrders.value.map {
            if (it.id == id) it.copy(status = status) else it
        }.toMutableList()
    }

    fun updateLoginUser(user: UserProfile) {
        _currentUser.value = user
    }
}
