package br.com.uvets.uvetsandroid.ui.signup

import br.com.uvets.uvetsandroid.business.network.BasicRxRequester
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseFactory
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class SignUpViewModel(userRepository: UserRepository) : BaseViewModel<SignUpNavigator>(userRepository) {

    fun register(name: String, doc: String, phone: String, email: String, address: String, password: String) {
        mNavigator?.showLoader(true)

        registerDisposable(
                userRepository.registerTutor(SignUpRequestVO(name, doc, phone, email, address, password))
                        .networkSchedulers()
                        .subscribeWith(object : BasicRxRequester<Void, SignUpNavigator>(this, mNavigator) {
                            override fun onSuccess(t: Void) {
                                mNavigator?.showSuccess("Cadastro realizado com sucesso!")
                                mNavigator?.goToLogin()
                            }
                        })
        )
    }
}
