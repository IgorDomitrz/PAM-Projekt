package com.example.pam_projekt

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pam_projekt.databinding.FragmentBasketBinding
import com.example.pam_projekt.placeholder.PlaceholderContent.PlaceholderItem

class MyItemRecyclerViewAdapter2(
    private val values: List<PlaceholderItem>,
    private val onItemClick: (PlaceholderItem) -> Unit
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentBasketBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: FragmentBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val idView: TextView = binding.itemNumber
        private val contentView: TextView = binding.content

        fun bind(item: PlaceholderItem) {
            idView.text = item.id
            contentView.text = item.content

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}