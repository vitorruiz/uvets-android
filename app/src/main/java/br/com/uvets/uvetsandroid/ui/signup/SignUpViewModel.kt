package br.com.uvets.uvetsandroid.ui.signup

import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class SignUpViewModel : BaseViewModel<SignUpNavigator>() {

    fun register(name: String, doc: String, phone: String, email: String, address: String, password: String) {
        mNavigator?.showLoader(true)

        registerDisposable(
            userRepository.registerTutor(SignUpRequestVO(name, doc, phone, email, address, password))
                .networkSchedulers()
                .subscribeWith(object : ViewModelRxRequester<User, SignUpNavigator>(this, mNavigator) {
                    override fun onSuccess(t: User) {
                        mNavigator?.showSuccess(R.string.message_successful_sign_up)
                        mNavigator?.onSignUpSuccess()
                    }
                })
        )
    }
}
