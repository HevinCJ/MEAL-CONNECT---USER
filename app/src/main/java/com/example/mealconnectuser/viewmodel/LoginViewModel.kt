package com.example.mealconnectuser.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealconnectuser.utils.NetworkResult
import com.example.mealconnectuser.utils.RegisterFieldState
import com.example.mealconnectuser.utils.RegisterValidation
import com.example.mealconnectuser.utils.validateEmail
import com.example.mealconnectuser.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel  @Inject constructor(private var firebaseAuth: FirebaseAuth):ViewModel() {

    private val _login = MutableStateFlow<NetworkResult<FirebaseUser>>(NetworkResult.Nodata())
    val login:Flow<NetworkResult<FirebaseUser>> = _login

    private val _validation = Channel<RegisterFieldState>()
    val validation =  _validation.receiveAsFlow()


     fun authenticateUserLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO){
            _login.emit(NetworkResult.Loading())

            if (checkValidation(email,password)){
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener { data ->
                    data.user?.let {
                        _login.value = NetworkResult.Success(it)
                    }
                }.addOnFailureListener {error->
                    error.message.let {
                        _login.value = NetworkResult.Error(null,error.message.toString())
                    }
                }
            }else{
                val emailValidation = async { validateEmail(email) }
                val passwordValidation = async { validatePassword(password) }

                val state = RegisterFieldState(emailValidation.await(), passwordValidation.await())

                _validation.send(state)
            }
        }
    }

    private fun checkValidation(email: String, password: String):Boolean {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        val isValidated  = emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
        return isValidated
    }
}