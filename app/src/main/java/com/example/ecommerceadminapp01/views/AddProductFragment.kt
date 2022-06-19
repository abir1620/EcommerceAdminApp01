package com.example.ecommerceadminapp01.views

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.ecommerceadminapp01.customdialogs.DatePickerFragment
import com.example.ecommerceadminapp01.databinding.FragmentAddProductBinding
import com.example.ecommerceadminapp01.models.Product
import com.example.ecommerceadminapp01.models.Purchase
import com.example.ecommerceadminapp01.utils.getFormattedDate
import com.example.ecommerceadminapp01.viewmodels.ProductViewModel
import com.google.firebase.Timestamp

class AddProductFragment : Fragment() {
   //Create an instance of productview model
    private val productViewModel: ProductViewModel by activityViewModels()
    private lateinit var binding: FragmentAddProductBinding
    private var category: String? = null
    private var timeStamp: Timestamp? = null
    private var imageUrl: String? = null
    private var bitmap: Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddProductBinding.inflate(inflater,container,false)
        //select category
        productViewModel.getAllCategories().observe(viewLifecycleOwner){
            if(!it.isNullOrEmpty()){
                //If it not null or Empty we will create adapter
                val adapter = ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, it)
                binding.selectCategorySpinner.adapter = adapter
            }
        }
        //add listener to the spinner
        binding.selectCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //which one user will select it will get by it
                //so we need to declare a variable in the above named category and type String
                category = parent!!.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //date button
        binding.selectDateBtn.setOnClickListener {
            DatePickerFragment {
                timeStamp = it
                binding.selectDateLevelTV.text = getFormattedDate(it.seconds * 1000, "dd/MM/yyyy")
            }.show(childFragmentManager, null)
        }

        //capture button
        binding.captureBtn.setOnClickListener {
            dispatchTakePictureIntent()
        }

        //save button
        binding.addProductBtn.setOnClickListener {

            val name=binding.productNameEt.text.toString()
            val purchasePrice=binding.purchasePriceEt.text.toString()
            val salePrice=binding.salePriceEt.text.toString()
            val description=binding.productDescriptionEt.text.toString()
            val quantity=binding.quantityEt.text.toString()

            //todo: validate fields

            binding.mProgressbar.visibility = View.VISIBLE

            productViewModel.uploadImage(bitmap!!) { downloadUrl ->
                //after uploading the image we will get the download url
                imageUrl = downloadUrl
                //Now I have to create an object of product Model and purchase model
                val product = Product(
                    name = name,
                    salePrice = salePrice.toDouble(),
                    description = description,
                    category = category,
                    imageUrl = imageUrl
                )

                val purchase = Purchase(
                    purchaseDate = timeStamp,
                    purchasePrice = purchasePrice.toDouble(),
                    quantity = quantity.toDouble()
                )
                //Now two object will insert two separate collection
                //now we will insert such a way so that if any one is failure to insert
                //both will show failed to insert
                //so we need a batch operation
                //so now we create a method in the productview model
                //named as addProduct()
                //After creating the addProductMethod() including two objects
                //respectively product model and purchase model
                // in the productViewModel we will send it in the
                //repository
                //so we will create a same method in the repository


             productViewModel.addProduct(product, purchase)




            }
        }
        //observe statusLD
        productViewModel.statusLD.observe(viewLifecycleOwner){
            if(it == "Success"){
            binding.mProgressbar.visibility= View.GONE
             resetFields()
            }else{
                binding.mProgressbar.visibility= View.GONE
                Toast.makeText(requireActivity(), "Failed to save", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun resetFields() {
        binding.productNameEt.text = null
        binding.purchasePriceEt.text = null
        binding.salePriceEt.text = null
        binding.productDescriptionEt.text = null
        binding.quantityEt.text = null


    }

    val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            bitmap = it.data?.extras?.get("data") as Bitmap
           /*productViewModel.uploadImage(bitmap){ downloadUrl->
               //after uploading the image we will get the download url
               imageUrl=downloadUrl
               //it won't execute here, it will execute after clicking the save button
           }
            */
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            resultLauncher.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

}