package com.example.ecommerceadminapp01.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadminapp01.models.Product
import com.example.ecommerceadminapp01.models.Purchase
import com.example.ecommerceadminapp01.utils.collectionCategory
import com.example.ecommerceadminapp01.utils.collectionProduct
import com.example.ecommerceadminapp01.utils.collectionPurchase
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    //create an instance of repository in the viewmodel class

    //create a reference of Firsebase firestore database
    val db= FirebaseFirestore.getInstance()

    //now it come from the product viewmodel to product repository
    //here it will be inserted
    fun addProduct(product: Product, purchase: Purchase, callback: (String) ->Unit){
        //for inserted at first I will create an object of write batch
        //write batch is like such kind of method by that we will take our
        // object in a queue
        val wb =db.batch()
        //we are inserted now, so we need to create two empty document
        //at first for product and then purchase
        val productDocRef = db.collection(collectionProduct).document()
        val purchaseDocRef = db.collection(collectionPurchase).document()

        product.id =productDocRef.id
        purchase.purchaseId = purchaseDocRef.id
        purchase.productId = product.id

        //set
        wb.set(productDocRef, product)
        wb.set(purchaseDocRef, purchase)
        wb.commit().addOnSuccessListener {
          //after successfully inserted the add product fragment will reset entirely
            //so we need a callback
            //If it success we will pass it in the callback
            callback("Success")
        }.addOnFailureListener {
           //If fail we will pass it in the callback
            callback("Failure")
        }


        //Now this method will call from productview model




    }


    fun getAllCategories() : LiveData<List<String>> {
        //create an instance of category live data
        val catLD = MutableLiveData<List<String>>()

        //now write the query
        db.collection(collectionCategory)
            .addSnapshotListener { value, error ->
                //First check either there has any  error or not
                if(error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<String>()
                for(doc in value!!.documents){
                   tempList.add(doc.get("name").toString())
                }
                catLD.value = tempList
            }
        return catLD
        /* call this method from viewmodel
        and return what it has returned
         */
    }

    fun getAllProducts() : LiveData<List<Product>>{
        val productLD = MutableLiveData<List<Product>>()
        //From which collection we will retrieve the data
        db.collection(collectionProduct)
            .addSnapshotListener { value, error ->
                if(error !=null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Product>()
                for(doc in value!!.documents){
                    doc.toObject(Product::class.java)?.let{ tempList.add(it)}
                }
                productLD.value = tempList
            }
        return productLD
    }
    //this same method again we will make in product view model


    fun getProductByProductId(id : String) : LiveData<Product>{
        val productLD = MutableLiveData<Product>()
        db.collection(collectionProduct).document(id)
            .addSnapshotListener{value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                productLD.value= value?.toObject(Product::class.java)

            }

   return productLD
    }

    fun getPurchaseHistoryByProductId(id : String) : LiveData<List<Purchase>>{
        val purchaseLD = MutableLiveData<List<Purchase>>()
        db.collection(collectionPurchase)
            .whereEqualTo("productId", id)
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<Purchase>()
                for(doc in value!!.documents){
                    doc.toObject(Purchase::class.java)?.let{tempList.add(it)}
                }
                purchaseLD.value = tempList
            }
        return purchaseLD
        //now make a shadow method in viewmodel
    }




}