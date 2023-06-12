package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    private lateinit var homeToSearchButton: Button
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView8: ImageView
    private lateinit var imageView9: ImageView
    private lateinit var imageView10: ImageView
    private lateinit var imageView11: ImageView
    private lateinit var filterEditText: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        homeToSearchButton = view.findViewById(R.id.homeToSearchButton)
        imageView4 = view.findViewById(R.id.imageView4)
        imageView5 = view.findViewById(R.id.imageView5)
        imageView6 = view.findViewById(R.id.imageView6)
        imageView7 = view.findViewById(R.id.imageView7)
        imageView8 = view.findViewById(R.id.imageView8)
        imageView9 = view.findViewById(R.id.imageView9)
        imageView10 = view.findViewById(R.id.imageView10)
        imageView11 = view.findViewById(R.id.imageView11)
        filterEditText = view.findViewById(R.id.searchInput)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeToSearchButton.setOnClickListener {
            val filterText = filterEditText.text.toString().trim()
            navigateToSearchFragment(filterText)
        }

        imageView4.setOnClickListener {
            navigateToSearchFragment("Smartfon")
        }
        imageView5.setOnClickListener {
            navigateToSearchFragment("Konsola")
        }

        imageView6.setOnClickListener {
            navigateToSearchFragment("Komputer")
        }

        imageView7.setOnClickListener {
            navigateToSearchFragment("Komponent")
        }

        imageView8.setOnClickListener {
            navigateToSearchFragment("Słuchawki")
        }

        imageView9.setOnClickListener {
            navigateToSearchFragment("Telewizor")
        }

        imageView10.setOnClickListener {
            navigateToSearchFragment("Peryferium")
        }

        imageView11.setOnClickListener {
            navigateToSearchFragment("Program")
        }
    }

    private fun navigateToSearchFragment(filter: String) {
        val bundle = Bundle().apply {
            putString("filter", filter)
        }
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment, bundle)
    }
}
