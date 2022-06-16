package com.example.ecommerceadminapp01.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadminapp01.models.Order
import com.example.ecommerceadminapp01.models.OrderDetails
import com.example.ecommerceadminapp01.utils.collectionOrder
import com.example.ecommerceadminapp01.utils.collectionOrderDetails
import com.google.firebase.firestore.FirebaseFirestore

class OrderRepository {
    val db=FirebaseFirestore.getInstance()


    //we need to add OrderSetting automatically by UI

   // fun AddOrderSettings() :



    //getOrderByUser
    //Admin will get the order lists by separately for individual user
    //That means he will get the orders by user id
    //Try to notify the admin while user will order
    //getAllOrders in order(no where clause)
    //getUser

    fun getAllOrder() : LiveData<List<Order>>{
        val orderLD = MutableLiveData<List<Order>>()
            db.collection(collectionOrder)
                .addSnapshotListener { value, error ->
                    if(error != null){
                        return@addSnapshotListener
                    }
                    val tempList = mutableListOf<Order>()
                    for(doc in value!!.documents){
                        doc.toObject(Order::class.java)?.let{tempList.add(it)}
                    }
                    orderLD.value = tempList
                }
        return orderLD
    }

    //Combine order and order details model
    //and pass the new model class in the adapter

    fun getOrderDetails() : LiveData<List<OrderDetails>>{
        val orderDetailsLD = MutableLiveData<List<OrderDetails>>()
        db.collection(collectionOrderDetails)
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<OrderDetails>()
                for(doc in value!!.documents){
                    doc.toObject(OrderDetails::class.java)?.let{tempList.add(it)}
                }
                orderDetailsLD.value = tempList
            }
        return orderDetailsLD
    }





}