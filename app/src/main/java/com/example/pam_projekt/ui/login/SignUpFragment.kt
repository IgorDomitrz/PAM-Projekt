package com.example.pam_projekt.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pam_projekt.MainActivity
import com.example.pam_projekt.R
import com.example.pam_projekt.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        mainActivity = activity as MainActivity

        binding.textView.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    mainActivity.signUp(email, pass)
                } else {
                    Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}