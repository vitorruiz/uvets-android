package br.com.uvets.uvetsandroid.ui.login

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
                    }

                    override fun onFail(responseCode: Int) {
                        if (responseCode == 401) {
                            mNavigator?.showError("Usuário ou senha inválidos")
                        } else {
                            super.onFail(responseCode)
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
}
