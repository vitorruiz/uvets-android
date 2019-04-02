package br.com.uvets.uvetsandroid.ui.login

import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class LoginViewModel(userRepository: UserRepository) : BaseViewModel<LoginNavigator>(userRepository) {

    fun login(email: String, password: String) {
        mNavigator?.showLoader(true)

        userRepository.authenticate(email, password, object : RestResponseListener<TokensVO> {
            override fun onSuccess(obj: TokensVO) {
                //saveUserToken(obj)
                fetchUser()
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showLoader(false)
                if (responseCode == 401) {
                    mNavigator?.showError("Usuário ou senha inválido")
                } else {
                    mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
                }
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showLoader(false)
                mNavigator?.showError(throwable.localizedMessage)
            }

            override fun onComplete() {}

        })
    }

    private fun fetchUser() {
        userRepository.fetchUser(object : RestResponseListener<User> {
            override fun onSuccess(obj: User) {
                //PrefsDataStore.saveUserData(obj!!)
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
        return userRepository.isUserAuthenticated
    }
}
