package com.example.pam_projekt

object ServiceBase {
    val serviceList: ArrayList<ServiceData> = ArrayList()
    var currentId: Int = 0
    init {

        serviceList.add(ServiceData(0, "Słuchawki", "Samsung", 100.0, "Testowe słuchawki"))
        serviceList.add(ServiceData(1, "Program", "CIA", 200.0, "Testowy program"))
        serviceList.add(ServiceData(2, "Smartfon", "Samsung", 1000.0, "Testowy smartfon"))
        serviceList.add(ServiceData(3, "Konsola", "Sony", 1500.0, "Testowa konsola"))
        currentId = 4
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

    fun pushItem(id: Int) {
        val service = serviceList.find { it.id == id }
        service?.let {
            val historyEntry = "Urządzenie $id przeniesiono do Detalu"
            service.updateHistoryLog(historyEntry)

            ItemBase.addItem(service.id, service.device, service.company, service.price, service.detail)
            serviceList.remove(service)
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
                serviceList[serviceList.indexOf(it)] = updatedService
                service.updateHistoryLog(historyEntry.toString())
            }
        }
    }

    fun getService(id: Int): ServiceData? {
        return serviceList.find { it.id == id }
    }
}