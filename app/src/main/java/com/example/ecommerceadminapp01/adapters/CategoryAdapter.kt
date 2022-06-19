package com.example.ecommerceadminapp01.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadminapp01.databinding.CategoryRowBinding
import com.example.ecommerceadminapp01.models.Product

class CategoryAdapter : ListAdapter<Product, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtil()) {

 class  CategoryViewHolder(val binding: CategoryRowBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind(product: Product){
                    binding.category = product
                }
            }

    class CategoryDiffUtil : DiffUtil.ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
       val binding = CategoryRowBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
       )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
       val category = getItem(position)
        holder.bind(category)
    }
}