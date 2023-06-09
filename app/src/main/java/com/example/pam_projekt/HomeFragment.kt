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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.homeToLoginButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
        view.findViewById<Button>(R.id.homeToSettingsButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
        view.findViewById<Button>(R.id.homeToBasketButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_basketFragment)
        }
        view.findViewById<Button>(R.id.homeToSearchButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        return view
    }
}
