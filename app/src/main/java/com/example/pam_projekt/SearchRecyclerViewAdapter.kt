package com.example.pam_projekt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchRecyclerViewAdapter(private var itemList: List<ItemBase.ItemData>) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    private var filteredItemList: List<ItemBase.ItemData> = itemList
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: ItemBase.ItemData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun updateItems(newItems: List<ItemBase.ItemData>) {
        itemList = newItems
        filterItems("")
    }

    fun filterItems(searchQuery: String) {
        filteredItemList = if (searchQuery.isEmpty()) {
            itemList
        } else {
            itemList.filter { item ->
                val device = item.device.lowercase()
                val company = item.company.lowercase()
                val price = item.price.toString()

                device.contains(searchQuery, ignoreCase = true) ||
                        company.contains(searchQuery, ignoreCase = true) ||
                        price.contains(searchQuery, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.fragment_search, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItemList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = filteredItemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deviceView: TextView = itemView.findViewById(R.id.item_number)
        private val companyView: TextView = itemView.findViewById(R.id.content)
        private val priceView: TextView = itemView.findViewById(R.id.price)
        private val detailView: TextView = itemView.findViewById(R.id.detail)
        private val itemImageView: ImageView = itemView.findViewById(R.id.item_image)

        fun bind(item: ItemBase.ItemData) {
            deviceView.text = item.device
            companyView.text = item.company
            priceView.text = item.price.toString()
            detailView.text = item.detail

            val drawableId = when (item.device) {
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
            itemImageView.setImageResource(drawableId)
        }
    }
}
