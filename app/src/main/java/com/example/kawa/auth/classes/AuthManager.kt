package com.example.kawa.auth.classes


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.channels.awaitClose

class AuthManager(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {

    // Check if user is currently logged in
    val currentUser: FirebaseUser?
        get() = auth.currentUser

    // Get a flow of the user state (Login/Logout changes)
    fun getAuthStateFlow() = kotlinx.coroutines.flow.callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose {
            auth.removeAuthStateListener(listener)
        }
    }
    suspend fun signUp(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Sign up failed: User is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun login(email: String, pass: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            val user = result.user
            if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Login failed: User is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
