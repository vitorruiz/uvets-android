package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import com.orhanobut.hawk.Hawk

class AppStorage : Storage {

    private val TAG = AppStorage::class.java.simpleName

    companion object {
        const val KEY_USER_TOKENS = "user_tokens"
        const val KEY_USER_DATA = "user_data"
    }

    override fun clearStorage() {
        Hawk.deleteAll()
    }

    override fun saveUserTokens(tokensVO: TokensVO) {
        Hawk.put(KEY_USER_TOKENS, tokensVO)
    }

    override fun getUserTokens(): TokensVO? {
        return Hawk.get(KEY_USER_TOKENS)
    }

    override fun saveUserData(user: User) {
        Hawk.put(KEY_USER_DATA, user)
    }

    override fun getUserData(): User? {
        return Hawk.get(KEY_USER_DATA)
    }

}