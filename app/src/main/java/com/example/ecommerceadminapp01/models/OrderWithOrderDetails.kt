package com.example.ecommerceadminapp01.models

import com.example.ecommerceadminapp01.utils.OrderStatus
import com.example.ecommerceadminapp01.utils.PaymentMethod
import com.google.firebase.Timestamp

data class OrderWithOrderDetails(
    var orderId: String? = null,
    var userId: String? = null,
    var orderDate: Timestamp? = null,
    var deliveryCharge: Int = 0,
    var discount: Int = 0,
    var vat: Int = 0,
    var grandTotal: Double? = null,
    var orderStatus: String = OrderStatus.pending,
    var paymentMethod: String = PaymentMethod.cod,
    var deliveryAddress: String? = null,

    var productId: String? = null,
    var productName: String? = null,
    var productPrice: Double = 0.0,
    var productQuantity: Int = 0
)
