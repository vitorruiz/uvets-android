package br.com.uvets.uvetsandroid.ui.login

import android.app.Application
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.prefs.PrefsDataStore
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel(application: Application) : BaseViewModel<LoginNavigator>(application) {

    private val mUserRepository = UserRepository()

    fun login(email: String, password: String) {
        mNavigator?.showLoader(true)

        mUserRepository.authenticate(email, password, object : RestResponseListener<String> {
            override fun onSuccess(obj: String) {
                saveUserToken(obj)
                fetchUser()
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

            override fun onComplete() {}

        })
    }

    private fun fetchUser() {
        mUserRepository.fetchUser(object : RestResponseListener<User?> {
            override fun onSuccess(obj: User?) {
                PrefsDataStore.saveUserData(obj!!)
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showError(throwable.localizedMessage)
            }

            override fun onComplete() {
                mNavigator?.showLoader(false)
                mNavigator?.onLoginSucceeded()
            }

        })
    }

    fun isUserAuthenticated(): Boolean {
        return !PrefsDataStore.getUserToken().isEmpty()
    }
}
