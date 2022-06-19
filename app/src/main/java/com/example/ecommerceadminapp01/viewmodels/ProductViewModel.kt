package com.example.ecommerceadminapp01.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceadminapp01.models.Product
import com.example.ecommerceadminapp01.models.Purchase
import com.example.ecommerceadminapp01.repositories.ProductRepository
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProductViewModel : ViewModel() {
    val repository = ProductRepository()
    //live data
    val statusLD = MutableLiveData<String>()

    fun addProduct(product: Product, purchase: Purchase){
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

        //call the addProduct() from the repository
        repository.addProduct(product, purchase){
            //so we need a live data
            //what will come in the message we will set it in the live data
            statusLD.value = it
            //Now it will be observed from addProductFragment

        }
    }

    fun getProducts() = repository.getAllProducts() /*This method will be called from view product fragment*/
    fun getProductByProductId(id: String) = repository.getProductByProductId(id)
    fun getPurchaseByProductId(id: String) = repository.getPurchaseHistoryByProductId(id)
    //These methods returns live data object
    //Now we will observe them from ProductDetailsFragment
    fun getAllCategories() = repository.getAllCategories()
    fun uploadImage(bitmap: Bitmap, callback: (String) -> Unit) {
        val photoRef = FirebaseStorage.getInstance().reference
            .child("images/${System.currentTimeMillis()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val data: ByteArray = baos.toByteArray()
        val uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                callback(downloadUri)
            } else {
                // Handle failures
                // ...
            }
        }
    }

}