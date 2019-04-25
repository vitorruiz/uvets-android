package br.com.uvets.uvetsandroid.ui.login

import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.business.network.HttpErrorBody
import br.com.uvets.uvetsandroid.business.network.SimpleRxRequester
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel : BaseViewModel<LoginNavigator>() {

    fun login(email: String, password: String) {
        mNavigator?.showLoader(true)
        registerDisposable(
            userRepository.authenticate(email, password)
                .networkSchedulers()
                .subscribeWith(object : ViewModelRxRequester<TokensVO, LoginNavigator>(this, mNavigator) {
                    override fun onSuccess(t: TokensVO) {
                        fetchUser()
                        registerDevice()
                    }

                    override fun onFail(httpErrorBody: HttpErrorBody) {
                        if (httpErrorBody.status == 401) {
                            mNavigator?.showError(R.string.message_username_or_password_invalid)
                        } else {
                            super.onFail(httpErrorBody)
                        }
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    private fun fetchUser() {
        registerDisposable(
            userRepository.fetchUser()
                .networkSchedulers()
                .subscribeWith(object : ViewModelRxRequester<User, LoginNavigator>(this, mNavigator) {
                    override fun onFinish() {
                        super.onFinish()
                        mNavigator?.onLoginSucceeded()
                    }
                })
        )
    }

    private fun registerDevice() {
        registerDisposable(
            userRepository.registerDevice()
                .networkSchedulers()
                .subscribeWith(object : SimpleRxRequester() {})
        )
    }
}
