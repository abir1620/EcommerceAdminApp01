package com.example.ecommerceadminapp01.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadminapp01.databinding.OrderDetailsRowBinding
import com.example.ecommerceadminapp01.models.OrderDetails


class OrderDetailsAdapter : ListAdapter<OrderDetails, OrderDetailsAdapter.OrderDetailsViewHolder>(OrderDetialsDiffUtil()){

    class OrderDetailsViewHolder(val binding: OrderDetailsRowBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(orderDetails: OrderDetails){
                    binding.orderDetails = orderDetails
                }
            }


    class  OrderDetialsDiffUtil : DiffUtil.ItemCallback<OrderDetails>(){
        override fun areItemsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
       val binding = OrderDetailsRowBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
       )
        return OrderDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val orderDetails = getItem(position)
        holder.bind(orderDetails)


    }
}