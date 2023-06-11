package com.example.pam_projekt

import java.util.*

object BasketBase {
    data class BasketData(val id: Int, val device: String, val company: String, val price: Double, val detail: String)

    private var nextId = 1
    val basketList: ArrayList<BasketData> = ArrayList()


    fun addBasket(id: Int, device: String, company: String, price: Double, detail: String) {
        val item = BasketData(id, device, company, price, detail)
        basketList.add(item)
        nextId++
    }

    fun removeBasket(id: Int) {
        val item = basketList.find { it.id == id }
        item?.let { basketList.remove(it) }
    }}