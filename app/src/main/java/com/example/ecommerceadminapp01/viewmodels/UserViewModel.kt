package com.example.ecommerceadminapp01.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ecommerceadminapp01.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {
    val userRepository = UserRepository()

    fun getCurrentUserId() = FirebaseAuth.getInstance().currentUser?.uid


    fun getUser(userId: String) =userRepository.getUser(userId)
}