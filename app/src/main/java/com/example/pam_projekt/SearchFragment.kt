package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_list, container, false)
        recyclerView = view.findViewById(R.id.list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickHandle()
        setupRecyclerView()
        populateRecyclerView()
    }

    private fun onClickHandle() {
        view?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = SearchRecyclerViewAdapter(ItemBase.itemList)
        adapter.setOnItemClickListener(object : SearchRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val item = ItemBase.itemList[position]
                val bundle = Bundle().apply {
                    putString("device", item.device)
                    putString("company", item.company)
                    putDouble("price", item.price)
                    putString("detail", item.detail)
                }
                findNavController().navigate(R.id.action_searchFragment_to_scrollingFragment, bundle)
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun populateRecyclerView() {
        val itemList = ItemBase.itemList
        adapter.updateItems(itemList)
    }
}
