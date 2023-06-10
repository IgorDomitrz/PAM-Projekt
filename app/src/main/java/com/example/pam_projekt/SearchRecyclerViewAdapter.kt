package com.example.pam_projekt

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class SearchRecyclerViewAdapter(private val itemList: ArrayList<ItemBase.ItemData>) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = ItemBase.getDevice(position)
        val company = ItemBase.getCompany(position)
        val price = ItemBase.getPrice(position)
        val detail = ItemBase.getDetail(position)

        holder.deviceView.text = device
        holder.companyView.text = company
        holder.priceView.text = price.toString()
        holder.detailView.text = detail

        val drawableId = when (device) {
            "Smartfon" -> R.drawable.ic_smartphone
            "Konsola" -> R.drawable.ic_console
            "Komputer" -> R.drawable.ic_computer
            "Podzespół" -> R.drawable.ic_component
            "Słuchawki" -> R.drawable.ic_audio
            "Telewizor" -> R.drawable.ic_tv
            "Peryferium" -> R.drawable.ic_periphery
            "Program" -> R.drawable.ic_program
            else -> R.drawable.logo_rakieta // Add a default drawable if needed
        }
        holder.itemImageView.setImageResource(drawableId)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deviceView: TextView = view.findViewById(R.id.item_number)
        val companyView: TextView = view.findViewById(R.id.content)
        val priceView: TextView = view.findViewById(R.id.price)
        val detailView: TextView = view.findViewById(R.id.detail)
        val itemImageView: ImageView = view.findViewById(R.id.item_image)
    }
}