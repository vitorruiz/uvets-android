package br.com.uvets.uvetsandroid.ui.signup

import android.app.Application
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class SignUpViewModel(application: Application) : BaseViewModel<SignUpNavigator>(application) {

    private val mUserRepository = UserRepository()

    fun register(name: String, doc: String, phone: String, email: String, address: String, password: String) {
        mNavigator?.showLoader(true)

        mUserRepository.registerTutor(SignUpRequestVO(name, doc, phone, email, address, password), object : RestResponseListener<String?> {
            override fun onSuccess(obj: String?) {
                mNavigator?.showSuccess("Cadastro realizado com sucesso!")
                mNavigator?.goToLogin()
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showError(throwable.localizedMessage)
            }

            override fun onComplete() {
                mNavigator?.showLoader(false)
            }

        })
    }
}
