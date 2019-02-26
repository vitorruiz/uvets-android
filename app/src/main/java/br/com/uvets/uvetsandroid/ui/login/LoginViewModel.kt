package br.com.uvets.uvetsandroid.ui.login

import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel : BaseViewModel<BaseNavigator>() {

    private val mUserRepository = UserRepository()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        mNavigator?.showLoader(true)

        mUserRepository.authenticate(email, password, object : RestResponseListener<String> {
            override fun onSuccess(obj: String) {
                onSuccess()
            }

            override fun onFail(responseCode: Int) {
                if (responseCode == 401) {
                    mNavigator?.showErrorMessage("Usuário ou senha inválido")
                }
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showErrorMessage("Erro inesperado")
            }

            override fun onComplete() {
                mNavigator?.showLoader(false)
            }

        })
    }
}
