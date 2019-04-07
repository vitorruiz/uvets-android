package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import io.reactivex.Observable
import retrofit2.Response

class UserRepository(val configuration: Configuration) {

    fun getUserData(): User? {
        return configuration.getStorage().getUserData()
    }

    fun logoutUser() {
        configuration.getStorage().clearStorage()
    }

    fun isUserAuthenticated() = (configuration.getStorage().getUserTokens() != null)

    fun authenticate(email: String, password: String): Observable<Response<TokensVO>> {
        return configuration.getApi().auth(LoginRequestVO(email, password)).doOnNext {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    configuration.getStorage().saveUserTokens(body)
                }
            }
        }
    }

    fun fetchUser(): Observable<Response<User>> {
        return configuration.getApiWithAuth().fetchUser().doOnNext {
            if (it.isSuccessful) {
                it.body()?.let { body ->
                    configuration.getStorage().saveUserData(body)
                }
            }
        }
    }

    fun registerTutor(signUpRequestVO: SignUpRequestVO): Observable<Response<Void>> {
        return configuration.getApi().registerTutor(signUpRequestVO)
    }
}