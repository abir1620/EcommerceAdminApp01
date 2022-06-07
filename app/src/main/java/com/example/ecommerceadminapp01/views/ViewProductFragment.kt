package com.example.ecommerceadminapp01.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceadminapp01.R
import com.example.ecommerceadminapp01.adapters.ProductAdapter
import com.example.ecommerceadminapp01.databinding.FragmentViewProductBinding
import com.example.ecommerceadminapp01.viewmodels.ProductViewModel


class ViewProductFragment : Fragment() {

    private lateinit var binding: FragmentViewProductBinding

    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentViewProductBinding.inflate(inflater, container, false)
        val adapter = ProductAdapter{ productId ->
            //After clicking any item it will take the id and it will go product details fragment
            findNavController().navigate(R.id.action_viewProductFragment_to_productDetailsFragment,
            args = bundleOf("id" to productId))
        }
        binding.productViewRecyclerview.layoutManager=GridLayoutManager(requireActivity(),2)
        binding.productViewRecyclerview.adapter = adapter

        //This product called from repository
        productViewModel.getProducts().observe(viewLifecycleOwner){
           //Now we get the list and we will pass the list in the adapter
            if(it.isEmpty()){
                binding.noItemFoundTv.visibility = View.VISIBLE
            }else{
                binding.noItemFoundTv.visibility = View.GONE
            }
            adapter.submitList(it)
        }

        return binding.root
    }
    //Now go to repository


}