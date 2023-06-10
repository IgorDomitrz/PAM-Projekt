package com.example.pam_projekt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
