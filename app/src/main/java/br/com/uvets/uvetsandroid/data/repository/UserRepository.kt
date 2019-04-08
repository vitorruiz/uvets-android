package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import io.reactivex.Observable

class UserRepository(val configuration: Configuration) {

    fun getUserData(): User? {
        return configuration.getStorage().getUserData()
    }

    fun logoutUser() {
        configuration.getStorage().clearStorage()
    }

    fun isUserAuthenticated() = (configuration.getStorage().getUserTokens() != null)

    fun authenticate(email: String, password: String): Observable<TokensVO> {
        return configuration.getApi().auth(LoginRequestVO(email, password)).doOnNext {
            configuration.getStorage().saveUserTokens(it)
        }
    }

    fun fetchUser(): Observable<User> {
        return configuration.getApiWithAuth().fetchUser().doOnNext {
            configuration.getStorage().saveUserData(it)
        }
    }

    fun registerTutor(signUpRequestVO: SignUpRequestVO): Observable<Void> {
        return configuration.getApi().registerTutor(signUpRequestVO)
    }
}