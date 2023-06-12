package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment(), SearchRecyclerViewAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        val filterText = arguments?.getString("filter") ?: ""
        val filteredItems = applyFilter(filterText)
        adapter.updateItems(filteredItems)
    }

    private fun setupRecyclerView() {
        adapter = SearchRecyclerViewAdapter(ArrayList())
        adapter.setOnItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun applyFilter(filterText: String): ArrayList<ItemBase.ItemData> {
        val filters = filterText.trim().split(" ")
        var filteredItems = ArrayList(ItemBase.itemList)

        for (filter in filters) {
            filteredItems = filterItems(filteredItems, filter)
        }

        return filteredItems
    }

    private fun filterItems(items: List<ItemBase.ItemData>, filter: String): ArrayList<ItemBase.ItemData> {
        val filteredItems = ArrayList<ItemBase.ItemData>()

        for (item in items) {
            if (isItemMatched(item, filter)) {
                filteredItems.add(item)
            }
        }

        return filteredItems
    }

    private fun isItemMatched(item: ItemBase.ItemData, filter: String): Boolean {
        val filterLower = filter.lowercase()

        return when {
            filterLower.matches(Regex("\\d+")) -> {
                item.price == filterLower.toDouble()
            }
            filterLower.matches(Regex("[<>]=?\\d+")) -> {
                val operator = filterLower.substring(0, 2)
                val price = filterLower.substring(2).toDouble()
                when (operator) {
                    "<" -> item.price < price
                    ">" -> item.price > price
                    "<=" -> item.price <= price
                    ">=" -> item.price >= price
                    "=<" -> item.price <= price
                    "=>" -> item.price >= price
                    else -> false
                }
            }
            filterLower.matches(Regex("\\d+-\\d+")) -> {
                val (lower, upper) = filterLower.split("-").map { it.toDouble() }
                item.price in lower..upper
            }
            else -> {
                item.device.lowercase() == filterLower || item.company.lowercase() == filterLower
            }
        }
    }

    override fun onItemClick(item: ItemBase.ItemData) {
        val bundle = Bundle().apply {
            putString("device", item.device)
            putString("company", item.company)
            putDouble("price", item.price)
            putString("detail", item.detail)
        }
        findNavController().navigate(R.id.action_searchFragment_to_scrollingFragment, bundle)
    }
}
