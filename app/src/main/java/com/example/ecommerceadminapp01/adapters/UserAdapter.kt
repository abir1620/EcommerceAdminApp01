package com.example.ecommerceadminapp01.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadminapp01.databinding.ManageUserRowBinding
import com.example.ecommerceadminapp01.models.EcomUser

class UserAdapter : ListAdapter<EcomUser, UserAdapter.UserViewHolder>(UserDiffUtil()) {

    class UserViewHolder(val binding: ManageUserRowBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(ecomUser: EcomUser){
                    binding.manageUser = ecomUser
                }
            }

    class UserDiffUtil : DiffUtil.ItemCallback<EcomUser>(){
        override fun areItemsTheSame(oldItem: EcomUser, newItem: EcomUser): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: EcomUser, newItem: EcomUser): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val binding = ManageUserRowBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
       )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val ecomUser = getItem(position)
        holder.bind(ecomUser)

    }
}