package com.example.pam_projekt

import android.os.Bundle
import android.os.Parcelable
import androidx.compose.runtime.internal.illegalDecoyCallException
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScrollingFragmentArgs(
    val device: String,
    val company: String,
    val price: Double,
    val detail: String
) : Parcelable {

    companion object {
        @JvmStatic
        fun fromBundle(bundle: Bundle): ScrollingFragmentArgs {
            val device = bundle.getString("device") ?: ""
            val company = bundle.getString("company") ?: ""
            val price = bundle.getDouble("price", 0.0)
            val detail = bundle.getString("detail") ?: ""
            return ScrollingFragmentArgs(device, company, price, detail)
        }
    }
}