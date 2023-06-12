package com.example.pam_projekt

object ItemBase {
    data class ItemData(val id: Int, val device: String, val company: String, val price: Double, val detail: String)

    private var nextId = 1
    val itemList: ArrayList<ItemData> = ArrayList()

    fun addItem(id: Int, device: String, company: String, price: Double, detail: String) {
        val item = ItemData(id, device, company, price, detail)
        itemList.add(item)
        notifyObservers()
    }

    fun removeItem(id: Int) {
        val item = itemList.find { it.id == id }
        item?.let { itemList.remove(it) }
    }
    fun getItem(id: Int): ItemData? {
        return itemList.find { it.id == id }
    }

    fun editItem(id: Int, device: String, company: String, price: Double, detail: String) {
        val item = itemList.find { it.id == id }
        item?.let {
            val updatedItem = ItemData(it.id, device, company, price, detail)
            itemList[itemList.indexOf(it)] = updatedItem
            notifyObservers()
        }
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
    interface ItemListObserver {
        fun onItemListChanged()
    }

    private val observers: MutableList<ItemListObserver> = mutableListOf()

    fun addObserver(observer: ItemListObserver) {
        observers.add(observer)
    }

    private fun notifyObservers() {
        observers.forEach { it.onItemListChanged() }
    }
}