package br.com.uvets.uvetsandroid.ui.login

import br.com.uvets.uvetsandroid.business.network.BasicRxRequester
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel(userRepository: UserRepository) : BaseViewModel<LoginNavigator>(userRepository) {

    fun login(email: String, password: String) {
        mNavigator?.showLoader(true)
        registerDisposable(
            userRepository.authenticate(email, password)
                .networkSchedulers()
                .subscribeWith(object : BasicRxRequester<TokensVO, LoginNavigator>(this, mNavigator) {
                    override fun onSuccess(t: TokensVO) {
                        fetchUser()
                    }
                })
        )
    }

    private fun fetchUser() {
        registerDisposable(
            userRepository.fetchUser()
                .networkSchedulers()
                .subscribeWith(object : BasicRxRequester<User, LoginNavigator>(this, mNavigator) {
                    override fun onFinish() {
                        super.onFinish()
                        mNavigator?.onLoginSucceeded()
                    }
                })
        )
    }

    fun isUserAuthenticated(): Boolean {
        return userRepository.isUserAuthenticated()
    }
}
