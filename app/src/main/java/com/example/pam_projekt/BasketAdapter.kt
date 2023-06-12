package com.example.pam_projekt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pam_projekt.databinding.ItemBasketBinding

class BasketAdapter(private val basketList: List<BasketBase.BasketData>) :
    RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(basketItem: BasketBase.BasketData) {
            binding.basketItem = basketItem
            binding.executePendingBindings()


            val deviceIconResId = getDeviceIconResId(basketItem.device)
            binding.basketLogo.setImageResource(deviceIconResId)
        }

        private fun getDeviceIconResId(device: String): Int {
            return when (device) {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val basketItem = basketList[position]
        holder.bind(basketItem)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }
}
