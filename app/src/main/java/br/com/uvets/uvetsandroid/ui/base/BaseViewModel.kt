package br.com.uvets.uvetsandroid.ui.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import br.com.uvets.uvetsandroid.data.prefs.PrefsDataStore
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository

open class BaseViewModel<N : BaseNavigator>(application: Application) : AndroidViewModel(application) {
    private val mUserRepository = UserRepository()
    protected var mNavigator: N? = null
    protected var mSharedPreferences = application.getSharedPreferences("uvets", Context.MODE_PRIVATE)

    fun attachNavigator(navigator: N) {
        mNavigator = navigator
    }

    protected fun saveUserToken(token: String) {
        PrefsDataStore.saveUserToken(token)
    }

    protected fun getUserToken(): String {
        return PrefsDataStore.getUserToken()
    }

    fun doLogout() {
        PrefsDataStore.clear()
        mNavigator?.onLogoutSucceeded()
    }

    protected fun refreshUserToken() {
        mUserRepository.refreshToken(object : RestResponseListener<String> {
            override fun onSuccess(obj: String) {
                saveUserToken(obj)
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showError("Sua sessão expirou, faça o login novamente")
                doLogout()
            }

            override fun onError(throwable: Throwable) {
                //refreshUserToken()
            }

            override fun onComplete() {

            }

        })
    }

}