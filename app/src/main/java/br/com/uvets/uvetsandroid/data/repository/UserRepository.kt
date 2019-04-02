package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepository(val configuration: Configuration) {

    fun getUserData(): User? {
        return configuration.getStorage().getUserData()
    }

    fun logoutUser() {
        configuration.getStorage().clearStorage()
    }

    val isUserAuthenticated = configuration.getStorage().getUserTokens() != null

    fun authenticate(email: String, password: String, callback: RestResponseListener<TokensVO>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApi().auth(LoginRequestVO(email, password))
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    configuration.getStorage().saveUserTokens(response.body()!!)
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }

    fun fetchUser(callback: RestResponseListener<User>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApiWithAuth().fetchUser()
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }

    fun registerTutor(signUpRequestVO: SignUpRequestVO, callback: RestResponseListener<Void?>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApi().registerTutor(signUpRequestVO)

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