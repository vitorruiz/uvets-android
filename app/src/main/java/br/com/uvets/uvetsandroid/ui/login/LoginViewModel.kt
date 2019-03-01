package br.com.uvets.uvetsandroid.ui.login

import android.app.Application
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel<BaseNavigator>(application) {

    private val mUserRepository = UserRepository()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        mNavigator?.showLoader(true)

        mUserRepository.authenticate(email, password, object : RestResponseListener<String> {
            override fun onSuccess(obj: String) {
                saveUserToken(obj)
                onSuccess()
            }

            override fun onFail(responseCode: Int) {
                if (responseCode == 401) {
                    mNavigator?.showError("Usuário ou senha inválido")
                } else {
                    mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
                }
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showError(throwable.localizedMessage)
            }

            override fun onComplete() {
                mNavigator?.showLoader(false)
            }

        })
    }

    private fun saveUserToken(token: String) {
        mSharedPreferences.edit()
            .putString("user_token", token)
            .apply()
    }

    fun isUserAuthenticated(): Boolean {
        return !mSharedPreferences.getString("user_token", null).isNullOrEmpty()
    }
}
