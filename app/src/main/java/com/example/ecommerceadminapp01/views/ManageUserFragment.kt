package com.example.ecommerceadminapp01.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceadminapp01.R
import com.example.ecommerceadminapp01.adapters.UserAdapter
import com.example.ecommerceadminapp01.databinding.FragmentManageUserBinding
import com.example.ecommerceadminapp01.viewmodels.OrderViewModel
import com.example.ecommerceadminapp01.viewmodels.UserViewModel

class ManageUserFragment : Fragment() {

    private lateinit var binding : FragmentManageUserBinding
    private val userViewModel : UserViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentManageUserBinding.inflate(inflater, container, false)
        val adapter = UserAdapter()
        binding.UserRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.UserRV.adapter = adapter
        userViewModel.getAllUser().observe(viewLifecycleOwner){userList ->
            adapter.submitList(userList)
        }
        return binding.root
    }


}