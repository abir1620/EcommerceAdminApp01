package com.example.ecommerceadminapp01.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadminapp01.databinding.DashboardItemBinding
import com.example.ecommerceadminapp01.models.DashboardItem
import com.example.ecommerceadminapp01.models.DashboardItemType
import com.example.ecommerceadminapp01.models.dashboardItemList

class DashboardItemAdapter(val callback: (DashboardItemType) -> Unit) : RecyclerView.Adapter<DashboardItemAdapter.DashboardViewHolder>(){

    class DashboardViewHolder(val binding: DashboardItemBinding)
        : RecyclerView.ViewHolder(binding.root){
            fun bind(item: DashboardItem){
                //we will pass this item into layout
                binding.item = item
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        //we have to create the object of binding
        val binding= DashboardItemBinding.inflate(
            LayoutInflater.from(parent.context),parent, false
        )
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(dashboardItemList.get(position))
        //add listener to the dashboard items
        holder.itemView.setOnClickListener {
            //after getting the position from the list we will pass its type through
            //callback to the DashboardFragment
            //so take a callback in the adapter class
            //when user click an item
            //we will call the callback function
            callback(dashboardItemList.get(position).type)
        }
    }

    override fun getItemCount(): Int = dashboardItemList.size

    /*
    for clicking the items we have to take a callback and pass it
    to the DashboardItemAdapter
     */


}