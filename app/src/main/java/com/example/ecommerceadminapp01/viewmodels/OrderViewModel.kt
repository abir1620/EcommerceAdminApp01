package com.example.ecommerceadminapp01.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceadminapp01.repositories.OrderRepository

class OrderViewModel: ViewModel() {
    val orderRepository = OrderRepository()
    val statusLD = MutableLiveData<String>()




    fun getAllOrder() = orderRepository.getAllOrder()

    fun getOrderDetails()=orderRepository.getOrderDetails()

}