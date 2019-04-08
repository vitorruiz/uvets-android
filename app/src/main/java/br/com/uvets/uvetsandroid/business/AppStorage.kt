package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.LocalStorage
import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO

class AppStorage(private val localStorage: LocalStorage) : Storage {

    private val TAG = AppStorage::class.java.simpleName

    companion object {
        const val KEY_USER_TOKENS = "user_tokens"
        const val KEY_USER_DATA = "user_data"
    }

    override fun clearStorage() {
        localStorage.clear()
    }

    override fun saveUserTokens(tokensVO: TokensVO) {
        localStorage.putSerialized(KEY_USER_TOKENS, tokensVO)
    }

    override fun getUserTokens(): TokensVO? {
        return localStorage.getSerialized(KEY_USER_TOKENS, null, TokensVO::class.java)
    }

    override fun saveUserData(user: User) {
        localStorage.putSerialized(KEY_USER_DATA, user)
    }

    override fun getUserData(): User? {
        return localStorage.getSerialized(KEY_USER_DATA, null, User::class.java)
    }

}