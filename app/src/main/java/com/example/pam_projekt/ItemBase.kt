package com.example.pam_projekt

import java.util.*

object ItemBase {
    data class ItemData(val id: Int, val device: String, val company: String, val price: Double, val detail: String)

    private var nextId = 1
    val itemList: ArrayList<ItemData> = ArrayList()

    init {
        //Testowanko
    }

    fun getNextId(): Int {
        return nextId++
    }

    fun addItem(id: Int, device: String, company: String, price: Double, detail: String) {
        val item = ItemData(id, device, company, price, detail)
        itemList.add(item)
    }

    fun removeItem(id: Int) {
        val item = itemList.find { it.id == id }
        item?.let { itemList.remove(it) }
    }

    fun getDevice(id: Int): String {
        return itemList.find { it.id == id }?.device.orEmpty()
    }

    fun getCompany(id: Int): String {
        return itemList.find { it.id == id }?.company.orEmpty()
    }

    fun getPrice(id: Int): Double {
        return itemList.find { it.id == id }?.price ?: 0.0
    }

    fun getDetail(id: Int): String {
        return itemList.find { it.id == id }?.detail.orEmpty()
    }
}