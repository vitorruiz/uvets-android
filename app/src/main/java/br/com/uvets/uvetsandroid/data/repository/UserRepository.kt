package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserRepository {

    fun authenticate(email: String, password: String, callback: RestResponseListener<String>) {

        GlobalScope.launch(Dispatchers.Default) {
            val request = getAuthService().auth(LoginRequestVO(email, password))
            try {
                val response = request.await()

                if (response.isSuccessful) {
                    callback.onSuccess(response.body().toString())
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }
        }

        callback.onComplete()
    }
}