package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController

class ServiceFragment : Fragment() {
    private lateinit var rootView: View
    private var currentItemId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_service, container, false)

        setupButtonListeners()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupButtonListeners() {
        val backButton = rootView.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            navigateBack()
        }

        val saveButton = rootView.findViewById<ImageButton>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveData()
        }

        val forwardButton = rootView.findViewById<ImageButton>(R.id.forwardButton)
        forwardButton.setOnClickListener {
            navigateForward()
        }

        val deleteButton = rootView.findViewById<ImageButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            deleteRecord()
        }

        val moveToDetailButton = rootView.findViewById<Button>(R.id.moveToDetailButton)
        moveToDetailButton.setOnClickListener {
            moveToDetail()
        }

        val restoreToServiceButton = rootView.findViewById<Button>(R.id.restoreToServiceButton)
        restoreToServiceButton.setOnClickListener {
            restoreToService()
        }

        val addButton = rootView.findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            addNewItem()
        }

        val searchButton = rootView.findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener {
            searchById()
        }
          }

    private fun saveData() {
        val device = rootView.findViewById<EditText>(R.id.deviceEditText).text.toString()
        val company = rootView.findViewById<EditText>(R.id.companyEditText).text.toString()
        val price = rootView.findViewById<EditText>(R.id.priceEditText).text.toString().toDoubleOrNull()
        val detail = rootView.findViewById<EditText>(R.id.detailEditText).text.toString()

        if (device.isNotBlank() && company.isNotBlank() && price != null && detail.isNotBlank()) {
            val service = ServiceBase.getService(currentItemId)

            if (service != null) {
                ServiceBase.editService(currentItemId, device, company, price, detail)
            } else {
                ServiceBase.addService(device, company, price, detail)
                currentItemId = ServiceBase.currentId - 1
            }

            Toast.makeText(requireContext(), "Data saved successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateBack() {
        currentItemId--
        displayItem(currentItemId)
    }

    private fun navigateForward() {
        currentItemId++
        displayItem(currentItemId)
    }

    private fun deleteRecord() {
        ServiceBase.removeService(currentItemId)
        Toast.makeText(requireContext(), "Record deleted successfully", Toast.LENGTH_SHORT).show()
        navigateForward()
    }

    private fun moveToDetail() {
        val service = ServiceBase.getService(currentItemId)

        if (service != null) {
            ServiceBase.pushItem(service.id)
            Toast.makeText(requireContext(), "Item moved to Detail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun restoreToService() {
        val item = ItemBase.itemList.find { it.id == currentItemId }

        if (item != null) {
            ServiceBase.pullItem(item.id)
            Toast.makeText(requireContext(), "Item restored to Service", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addNewItem() {
        val device = rootView.findViewById<EditText>(R.id.deviceEditText).text.toString()
        val company = rootView.findViewById<EditText>(R.id.companyEditText).text.toString()
        val price = rootView.findViewById<EditText>(R.id.priceEditText).text.toString().toDoubleOrNull()
        val detail = rootView.findViewById<EditText>(R.id.detailEditText).text.toString()

        if (device.isNotBlank() && company.isNotBlank() && price != null && detail.isNotBlank()) {
            ServiceBase.addService(device, company, price, detail)
            currentItemId = ServiceBase.currentId - 1

            Toast.makeText(requireContext(), "New item added successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun searchById() {
        val idEditText = rootView.findViewById<EditText>(R.id.searchEditText)
        val id = idEditText.text.toString().toIntOrNull()

        if (id != null) {
            val item = ServiceBase.getService(id)

            if (item != null) {
                currentItemId = item.id
                displayItem(currentItemId)
            } else {
                Toast.makeText(requireContext(), "Nie znaleziono ID", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Wpisz poprawne ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayItem(itemId: Int) {
        currentItemId = itemId

        val deviceEditText = rootView.findViewById<EditText>(R.id.deviceEditText)
        val companyEditText = rootView.findViewById<EditText>(R.id.companyEditText)
        val priceEditText = rootView.findViewById<EditText>(R.id.priceEditText)
        val detailEditText = rootView.findViewById<EditText>(R.id.detailEditText)
        val idTextView = rootView.findViewById<TextView>(R.id.idTextView)
        val locationTextView = rootView.findViewById<TextView>(R.id.locationTextView)

        val service = ServiceBase.getService(itemId)

        if (service != null) {
            deviceEditText.setText(service.device)
            companyEditText.setText(service.company)
            priceEditText.setText(service.price.toString())
            detailEditText.setText(service.detail)
            idTextView.text = service.id.toString()
            locationTextView.text = "Serwisie"
        } else {
            val item = ItemBase.itemList.find { it.id == itemId }

            if (item != null) {
                deviceEditText.setText(item.device)
                companyEditText.setText(item.company)
                priceEditText.setText(item.price.toString())
                detailEditText.setText(item.detail)
                idTextView.text = item.id.toString()
                locationTextView.text = "Detalu"
            } else {
                deviceEditText.setText("")
                companyEditText.setText("")
                priceEditText.setText("")
                detailEditText.setText("")
                idTextView.text = ""
                locationTextView.text = ""
            }
        }
    }
}
