package com.example.pam_projekt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Button click listener to navigate to FragmentLogin
        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        return view
    }
}