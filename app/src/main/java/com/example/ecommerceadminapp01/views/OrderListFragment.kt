package com.example.ecommerceadminapp01.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceadminapp01.R
import com.example.ecommerceadminapp01.adapters.OrderAdapter
import com.example.ecommerceadminapp01.databinding.FragmentOrderListBinding
import com.example.ecommerceadminapp01.viewmodels.OrderViewModel
import com.example.ecommerceadminapp01.viewmodels.UserViewModel


class OrderListFragment : Fragment() {

    private lateinit var binding: FragmentOrderListBinding
    private val orderViewModel: OrderViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val adapter = OrderAdapter{orderId->
            findNavController().navigate(R.id.action_orderFragment_to_orderDetailsFragment,
            bundleOf("orderId" to orderId))

        }
        binding.orderRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.orderRV.adapter = adapter

        orderViewModel.getAllOrder().observe(viewLifecycleOwner){orderList->
                adapter.submitList(orderList)
            }

        return binding.root
    }


}