package br.com.uvets.uvetsandroid.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LoginViewModel : BaseViewModel() {

    fun login(email: String, password: String, onSuccess: () -> Unit) {

        GlobalScope.launch(Dispatchers.Default) {
            isLoadingLiveData.postValue(true)

            delay(3, TimeUnit.SECONDS)
            if (email == "admin" && password == "admin") {
                onSuccess()
            } else {
                errorMessageLiveData.postValue("Usuário ou senha inválido")
            }

            isLoadingLiveData.postValue(false)
        }
    }
}
