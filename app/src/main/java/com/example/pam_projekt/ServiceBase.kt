package com.example.pam_projekt

import java.util.*

object ServiceBase {
    val serviceList: ArrayList<ServiceData> = ArrayList()
    var currentId: Int = 0

    data class ServiceData(val id: Int, val device: String, val company: String, val price: Double, val detail: String)

    fun addService(device: String, company: String, price: Double, detail: String) {
        val service = ServiceData(currentId++, device, company, price, detail)
        serviceList.add(service)
    }

    fun removeService(id: Int) {
        val service = serviceList.find { it.id == id }
        service?.let { serviceList.remove(it) }
    }

    fun pushItem(id: Int) {
        val service = serviceList.find { it.id == id }
        service?.let {
            ItemBase.addItem(it.id, it.device, it.company, it.price, it.detail)
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
            serviceList.add(service)
            ItemBase.removeItem(id)
        }
    }

    fun editService(id: Int, device: String, company: String, price: Double, detail: String) {
        val service = serviceList.find { it.id == id }
        service?.let {
            val updatedService = ServiceData(it.id, device, company, price, detail)
            serviceList[serviceList.indexOf(it)] = updatedService
        }
    }

    fun getService(id: Int): ServiceData? {
        return serviceList.find { it.id == id }
    }
}
