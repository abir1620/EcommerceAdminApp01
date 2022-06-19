package com.example.ecommerceadminapp01.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceadminapp01.models.EcomUser
import com.example.ecommerceadminapp01.models.Order
import com.example.ecommerceadminapp01.utils.collectionOrder
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

    fun getAllUser() : LiveData<List<EcomUser>>{
        val userLD = MutableLiveData<List<EcomUser>>()
        db.collection(collectionUser)
            .addSnapshotListener { value, error ->
                if(error != null){
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<EcomUser>()
                for(doc in value!!.documents){
                    doc.toObject(EcomUser::class.java)?.let{tempList.add(it)}
                }
                userLD.value = tempList
            }
        return userLD
    }



}