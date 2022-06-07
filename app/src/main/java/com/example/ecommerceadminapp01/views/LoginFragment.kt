package com.example.ecommerceadminapp01.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ecommerceadminapp01.databinding.FragmentLoginBinding
import com.example.ecommerceadminapp01.viewmodels.LoginViewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentLoginBinding.inflate(inflater, container, false)
        //observe the livedata
        loginViewModel.authLD.observe(viewLifecycleOwner){
            if(it == LoginViewModel.Authentication.AUTHENTICATED){
                findNavController().popBackStack()
            }
        }
        loginViewModel.errorMsgLD.observe(viewLifecycleOwner){
            binding.errorMsgTv.text = it
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            //todo: validates fields

            Toast.makeText(requireActivity(), "login clicked: $email $pass", Toast.LENGTH_SHORT).show()

            loginViewModel.loginAdmin(email, pass)

        }
        return binding.root
    }

}