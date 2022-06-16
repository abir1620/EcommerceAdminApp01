package com.example.ecommerceadminapp01.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadminapp01.models.EcomUser
import com.example.ecommerceadminapp01.utils.collectionUser
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val db = FirebaseFirestore.getInstance()


    fun getUser(userId: String) : LiveData<EcomUser>{
        val userLD = MutableLiveData<EcomUser>()
            db.collection(collectionUser)
                .document(userId)
                .addSnapshotListener { value, error ->
                    if(error != null){
                        return@addSnapshotListener
                    }
                    userLD.value = value!!.toObject(EcomUser::class.java)
                }
        return userLD
    }

}