package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getAuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository {

    fun authenticate(email: String, password: String, callback: RestResponseListener<String>) {

        GlobalScope.launch(Dispatchers.Main) {
            val request = getAuthService().auth(LoginRequestVO(email, password))
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    callback.onSuccess(response.body().toString())
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }

    fun registerTutor(signUpRequestVO: SignUpRequestVO, callback: RestResponseListener<String?>) {

        GlobalScope.launch(Dispatchers.Main) {
            val request = getAuthService().registerTutor(signUpRequestVO)
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    callback.onSuccess(null)
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }
}