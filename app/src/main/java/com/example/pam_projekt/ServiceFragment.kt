package com.example.pam_projekt

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.*
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException

class ServiceFragment : Fragment() {
    private lateinit var rootView: View
    private var currentItemId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_service, container, false)

        setupButtonListeners()
        displayItem(0) // Display item with ID 0
        generateQRCode()
        ItemBase.addObserver(object : ItemBase.ItemListObserver {
            override fun onItemListChanged() {
                generateQRCode()
            }
        })
        return rootView
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
            val item = ItemBase.getItem(currentItemId)
            if (service != null) {
                ServiceBase.editService(currentItemId, device, company, price, detail)
            } else if (item != null) {
                ItemBase.editItem(currentItemId, device, company, price, detail)
            }

            Toast.makeText(requireContext(), "Pomyślnie zapisano dane", Toast.LENGTH_SHORT).show()
            displayItem(currentItemId)
            generateQRCode()
        } else {
            Toast.makeText(requireContext(), "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateBack() {
        currentItemId--
        displayItem(currentItemId)
        generateQRCode()
    }

    private fun navigateForward() {
        currentItemId++
        displayItem(currentItemId)
        generateQRCode()
    }

    private fun deleteRecord() {
        ServiceBase.removeService(currentItemId)
        Toast.makeText(requireContext(), "Rekord pomyślnie usunięty", Toast.LENGTH_SHORT).show()
        navigateForward()
        generateQRCode()
    }

    private fun moveToDetail() {
        val service = ServiceBase.getService(currentItemId)

        if (service != null) {
            ServiceBase.pushItem(service.id,service.device,service.company,service.price,service.detail)
            Toast.makeText(requireContext(), "Produkt przekazany do detalu", Toast.LENGTH_SHORT).show()
            displayItem(service.id)
            generateQRCode()
        }
    }

    private fun restoreToService() {
        val item = ItemBase.itemList.find { it.id == currentItemId }

        if (item != null) {
            ServiceBase.pullItem(item.id)
            Toast.makeText(requireContext(), "Produkt przekazany do serwisu", Toast.LENGTH_SHORT).show()
            displayItem(item.id)
            generateQRCode()
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

            Toast.makeText(requireContext(), "Udało się dodać produkt", Toast.LENGTH_SHORT).show()
            displayItem(currentItemId)
            generateQRCode()
        } else {
            Toast.makeText(requireContext(), "Wypełnij wszystkie pola!", Toast.LENGTH_SHORT).show()
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
                generateQRCode()
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
                // Clear form fields when there is no matching item
                deviceEditText.text = null
                companyEditText.text = null
                priceEditText.text = null
                detailEditText.text = null
                idTextView.text = ""
                locationTextView.text = ""
            }
        }
    }

    private fun generateQRCode() {
        val service = ServiceBase.getService(currentItemId)

        if (service != null) {
            val qrCodeText = generateQRCodeText(service)
            val qrCodeBitmap = generateQRCodeBitmap(qrCodeText)
            val generateQRCodeImageView = rootView.findViewById<ImageView>(R.id.generateQRCode)
            generateQRCodeImageView.setImageBitmap(qrCodeBitmap)
        }
    }

    private fun generateQRCodeText(service: ServiceBase.ServiceData): String {
        val historyEntries = service.historyList.joinToString("\n") { it.description }
        return buildString {
            append("Id: \"${service.id}\"\n")
            append("Produkt: \"${service.device}\"\n")
            append("Marka: \"${service.company}\"\n")
            append("Cena: \"${service.price}\"\n")
            append("Opis: \"${service.detail}\"\n")
            append("-----------------------------\n")
            append(historyEntries)
        }
    }

    private fun generateQRCodeBitmap(qrCodeText: String): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        try {
            val bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] = if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            throw RuntimeException("Nie udało się wygenerować kodu QR", e)
        }
    }
}
