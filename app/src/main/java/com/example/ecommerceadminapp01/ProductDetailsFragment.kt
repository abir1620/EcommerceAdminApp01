package com.example.ecommerceadminapp01

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadminapp01.databinding.FragmentProductDetailsBinding
import com.example.ecommerceadminapp01.utils.getFormattedDate
import com.example.ecommerceadminapp01.viewmodels.ProductViewModel


class ProductDetailsFragment : Fragment() {
   private lateinit var binding: FragmentProductDetailsBinding
   private val productViewModel: ProductViewModel by activityViewModels()
    private var id: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProductDetailsBinding.inflate(inflater, container, false)

        //when we go from view product fragment to product details fragment we go
        //with product Id
        //So here we receive this id
        //declare it at the class level
        id = arguments?.getString("id")
        //check the id
        id?.let {
            //Now by using this id we will take the field of product and purchase
            //Go to repository
            //when id available
            productViewModel.getProductByProductId(it).observe(viewLifecycleOwner){
                binding.product = it
            }

        }

        id?.let{
            productViewModel.getPurchaseByProductId(it).observe(viewLifecycleOwner){
                var purchaseTxt = ""
                it.forEach {
                    purchaseTxt += "${getFormattedDate(it.purchaseDate!!.seconds * 1000, "dd/MM/yyyy")} - Qty: ${it.quantity} - Price: ${it.purchasePrice}\n"
                }
                binding.purchaseListTV.text = purchaseTxt
            }
        }


        return binding.root

    }

}