package com.example.pam_projekt

import android.os.Bundle
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        onClickHandle()

    }

    private fun onClickHandle() {
        requireActivity().onBackPressedDispatcher.addCallback {
         findNavController().popBackStack()
        }
    }
}
