package com.example.projobliveapp.Screens.Login

import com.example.projobliveapp.models.MUser
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _userData = MutableLiveData<MUser?>()
    val userData: LiveData<MUser?> = _userData

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    /**
     * Sign in the user with email and password and navigate to the home screen if successful.
     */
    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            fetchUserData()
                            home()
                        } else {
                            Log.d("FB", "Sign-in failed: ${task.exception?.message}")
                        }
                    }
            } catch (ex: Exception) {
                Log.d("FB", "Exception during sign-in: ${ex.message}")
            }
        }
    /**
     * Create a new user with email and password, save additional user information in Firestore,
     * and navigate to the home screen if successful.
     */
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        location: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            val displayName = email.split('@')[0]
                            createUser(displayName, firstName, lastName, location)
                            home()
                        } else {
                            Log.d("FB", "User ID is null after registration")
                        }
                    } else {
                        Log.d("FB", "Registration failed: ${task.exception?.message}")
                    }
                    _loading.value = false
                }
        }
    }

    /**
     * Save user information to Firestore using the userId as the document ID.
     */
    private fun createUser(displayName: String?, firstName: String, lastName: String, location: String) {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            val user = MUser(
                userId = userId,
                displayName = displayName ?: "",
                firstName = firstName,
                lastName = lastName,
                location = location,
                avatarUrl = "",
                quote = "Life is great",
                profession = "Android Developer",
                id = null
            )

            firestore.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener {
                    Log.d("FB", "User successfully created with ID: $userId")
                }
                .addOnFailureListener { e ->
                    Log.w("FB", "Error creating user document", e)
                }
        } else {
            Log.d("FB", "User ID is null, cannot create user document")
        }
    }

    /**
     * Fetch the logged-in user's data from Firestore.
     */
    private fun fetchUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            _loading.value = true
            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        Log.d("FB", "Document exists for userId: $userId")
                        val user = document.toObject(MUser::class.java)
                        _userData.value = user
                    } else {
                        Log.d("FB", "No document found for userId: $userId")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("FB", "Error fetching document for userId: $userId", exception)
                }
        } else {
            Log.d("FB", "User ID is null, cannot fetch user data")
            _userData.value = null
            _loading.value = false
        }
    }

    /**
     * Observe userData and loading state for debugging.
     */
    fun observeDebugging() {
        userData.observeForever { user ->
            if (user != null) {
                Log.d("Debug", "Observed userData: $user")
            } else {
                Log.d("Debug", "Observed userData is null")
            }
        }

        loading.observeForever { isLoading ->
            Log.d("Debug", "Observed loading state: $isLoading")
        }
    }
}
