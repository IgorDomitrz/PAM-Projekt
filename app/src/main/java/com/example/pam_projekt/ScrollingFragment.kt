package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pam_projekt.databinding.FragmentScrollingBinding

class ScrollingFragment : Fragment() {
    private var _binding: FragmentScrollingBinding? = null
    private val binding get() = _binding!!

    private val args: ScrollingFragmentArgs by lazy {
        ScrollingFragmentArgs.fromBundle(requireArguments())
    }

    private val viewModel: BasketViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScrollingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewIcon.setImageResource(R.drawable.logo_rakieta)
        binding.textViewDevice.text = args.device
        binding.textViewCompany.text = args.company
        binding.textViewPrice.text = args.price.toString()
        binding.textViewDetail.text = args.detail
        changeIcon()

        view.findViewById<Button>(R.id.gotoBasket)?.setOnClickListener {
            val device = args.device
            val company = args.company
            val price = args.price
            val detail = args.detail

            val itemAlreadyExists = BasketBase.basketList.any { it.device == device && it.company == company }

            if (itemAlreadyExists) {
                Toast.makeText(requireContext(), "Item is already in the basket", Toast.LENGTH_SHORT).show()
            } else {
                BasketBase.addBasket(id, device, company, price, detail)
                Toast.makeText(requireContext(), "Item added to the basket", Toast.LENGTH_SHORT).show()
            }

            findNavController().navigate(R.id.action_scrollingFragment_to_basketFragment)
        }
    }

    private fun changeIcon() {
        val drawableId = when (args.device) {
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
        binding.imageViewIcon.setImageResource(drawableId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
