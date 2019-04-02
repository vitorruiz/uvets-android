package br.com.uvets.uvetsandroid.ui.signup

import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseFactory
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class SignUpViewModel(userRepository: UserRepository) : BaseViewModel<SignUpNavigator>(userRepository) {

    fun register(name: String, doc: String, phone: String, email: String, address: String, password: String) {
        mNavigator?.showLoader(true)

        userRepository.registerTutor(
            SignUpRequestVO(name, doc, phone, email, address, password),
            object : RestResponseFactory<Void?, SignUpNavigator>(mNavigator, this) {
                override fun onSuccess(obj: Void?) {
                    mNavigator?.showSuccess("Cadastro realizado com sucesso!")
                    mNavigator?.goToLogin()
                }
            })
    }
}
