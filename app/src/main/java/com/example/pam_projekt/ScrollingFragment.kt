package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.pam_projekt.databinding.FragmentScrollingBinding

class ScrollingFragment : Fragment() {
    private var _binding: FragmentScrollingBinding? = null
    private val binding get() = _binding!!

    private val args: ScrollingFragmentArgs by lazy {
        ScrollingFragmentArgs.fromBundle(requireArguments())
    }

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

    }

    private fun changeIcon(){
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
        view?.findViewById<ImageView>(R.id.imageViewIcon)?.setImageResource(drawableId);
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
