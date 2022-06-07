package com.example.ecommerceadminapp01.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    //User is or is not
    //to check it by using an enum value
    enum class Authentication{
        AUTHENTICATED, UNAUTHENTICATED
    }
    //take a live data for
    val authLD: MutableLiveData<Authentication> = MutableLiveData()
    val errorMsgLD: MutableLiveData<String> = MutableLiveData()
    //we need a firebase authentication object
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()



    //now we will execute the init block
    //when init block will execute?
    //when the viewmodel object will be created
    //we will initialize our value into init block
    init {
        //if user already logged in or not
        //that will assign authLD
        //that will depend auth instance
        if(auth.currentUser !=null){
            authLD.value=Authentication.AUTHENTICATED
        }else{
            authLD.value=Authentication.UNAUTHENTICATED
            //As it is LiveData and its value changes
            //so it will be observed from its Fragment(LoginFragment)
        }

    }

    fun loginAdmin(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                val uid = auth.currentUser!!.uid
                db.collection("Admins")
                    .document(uid)
                    .get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            authLD.value = Authentication.AUTHENTICATED
                        } else {
                            errorMsgLD.value = "You are not an Admin"
                            auth.signOut()
                        }
                    }.addOnFailureListener {
                        errorMsgLD.value = "You are not an Admin"
                        auth.signOut()
                    }
                //authLD.value = Authentication.AUTHENTICATED
            }.addOnFailureListener {
                errorMsgLD.value = it.localizedMessage
            }
    }
}