package com.example.ecommerceadminapp01.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceadminapp01.R
import com.example.ecommerceadminapp01.adapters.OrderDetailsAdapter
import com.example.ecommerceadminapp01.databinding.FragmentOrderDetailsBinding
import com.example.ecommerceadminapp01.viewmodels.OrderViewModel
import com.example.ecommerceadminapp01.viewmodels.UserViewModel


class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private var orderId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        val adapter = OrderDetailsAdapter()

        binding.orderDetailsRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.orderDetailsRV.adapter = adapter

        orderId = arguments?.getString(orderId)

        orderId?.let {
            orderViewModel.getOrderDetails(orderId!!).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }


        return binding.root
    }

}