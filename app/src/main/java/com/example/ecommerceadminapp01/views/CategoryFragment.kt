package com.example.ecommerceadminapp01.views

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceadminapp01.adapters.CategoryAdapter
import com.example.ecommerceadminapp01.databinding.FragmentCategoryBinding
import com.example.ecommerceadminapp01.viewmodels.ProductViewModel


class CategoryFragment : Fragment() {

    private lateinit var binding : FragmentCategoryBinding
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val adapter = CategoryAdapter()
        binding.categoryRV.layoutManager = LinearLayoutManager(requireActivity())
        binding.categoryRV.adapter = adapter
        productViewModel.getAllCategories().observe(viewLifecycleOwner){
            //adapter.submitList(it)
        }
        return binding.root
    }

}