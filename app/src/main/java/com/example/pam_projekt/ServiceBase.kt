package com.example.pam_projekt

object ServiceBase {
    val serviceList: ArrayList<ServiceData> = ArrayList()
    var currentId: Int = 0
    init {
        // Add sample data
        serviceList.add(ServiceData(0, "Smartfon", "Samsung", 100.0, "Testowy smartfon"))
        serviceList.add(ServiceData(1, "Konsola", "Samsung", 200.0, "Testowa konsola"))
        serviceList.add(ServiceData(2, "Komputer", "Samsung", 300.0, "Testowy komputer"))
        serviceList.add(ServiceData(3, "Podzespół", "Samsung", 400.0, "Testowy podzespół"))
        serviceList.add(ServiceData(4, "Słuchawki", "Sony", 100.0, "Testowe słuchawki"))
        serviceList.add(ServiceData(5, "Telewizor", "Sony", 200.0, "Testowy telewizor"))
        serviceList.add(ServiceData(6, "Peryferium", "Sony", 300.0, "Testowe peryferia"))
        serviceList.add(ServiceData(7, "Program", "Sony", 400.0, "Testowy program"))
        currentId = 8
    }
    data class ServiceData(
        val id: Int,
        var device: String,
        var company: String,
        var price: Double,
        var detail: String
    ) {
        data class HistoryEntry(val timestamp: String, val description: String)

        val historyList: MutableList<HistoryEntry> = mutableListOf()

        fun updateHistoryLog(logEntry: String) {
            val timestamp = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", java.util.Date()).toString()
            val historyEntry = HistoryEntry(timestamp, logEntry)
            historyList.add(historyEntry)
        }
    }

    fun addService(device: String, company: String, price: Double, detail: String) {
        val service = ServiceData(currentId++, device, company, price, detail)
        serviceList.add(service)

        val historyEntry = "Dodano rekord ${service.id} ${service.device} ${service.company} ${service.price} ${service.detail}"
        service.updateHistoryLog(historyEntry)
    }

    fun removeService(id: Int) {
        val service = serviceList.find { it.id == id }
        service?.let { serviceList.remove(it) }
    }

    fun pushItem(id: Int, device: String, company: String, price: Double, detail: String) {
        val service = serviceList.find { it.id == id }
        service?.let {
            val historyEntry = "Urządzenie $id przeniesiono do Detalu"
            it.updateHistoryLog(historyEntry)
            ItemBase.addItem(id, device, company, price, detail)
            serviceList.remove(it)
        }
    }

    fun pullItem(id: Int) {
        val item = ItemBase.itemList.find { it.id == id }
        item?.let {
            val device = it.device
            val company = it.company
            val price = it.price
            val detail = it.detail
            val service = ServiceData(id, device, company, price, detail)

            val historyEntry = "Urządzenie $id przeniesiono do Serwisu"
            service.updateHistoryLog(historyEntry)

            serviceList.add(service)
            ItemBase.removeItem(id)
        }
    }

    fun editService(id: Int, device: String, company: String, price: Double, detail: String) {
        val service = serviceList.find { it.id == id }
        service?.let {
            val updatedService = ServiceData(it.id, device, company, price, detail)

            val historyEntry = StringBuilder()

            if (service.device != device) {
                historyEntry.append("Zmieniono typ produktu ${service.id} z \"${service.device}\" na \"$device\"\n")
                updatedService.updateHistoryLog("Zmieniono typ produktu ${service.id} z \"${service.device}\" na \"$device\"")
            }

            if (service.company != company) {
                historyEntry.append("Zmieniono markę produktu ${service.id} z \"${service.company}\" na \"$company\"\n")
                updatedService.updateHistoryLog("Zmieniono markę produktu ${service.id} z \"${service.company}\" na \"$company\"")
            }

            if (service.price != price) {
                historyEntry.append("Zmieniono cenę produktu ${service.id} z \"${service.price}\" na \"$price\"\n")
                updatedService.updateHistoryLog("Zmieniono cenę produktu ${service.id} z \"${service.price}\" na \"$price\"")
            }

            if (service.detail != detail) {
                historyEntry.append("Zaktualizowano opis produktu ${service.id}\n")
                updatedService.updateHistoryLog("Zaktualizowano opis produktu ${service.id}")
            }

            if (historyEntry.isNotEmpty()) {
                val index = serviceList.indexOf(it)
                serviceList[index] = updatedService
                service.updateHistoryLog(historyEntry.toString())
            }
        }
    }

    fun getService(id: Int): ServiceData? {
        return serviceList.find { it.id == id }
    }
}