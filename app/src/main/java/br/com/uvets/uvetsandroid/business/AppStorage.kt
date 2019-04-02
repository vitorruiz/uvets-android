package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import com.orhanobut.hawk.Hawk

class AppStorage : Storage {
    override fun clearStorage() {
        Hawk.deleteAll()
    }

    override fun saveUserTokens(tokensVO: TokensVO) {
        Hawk.put("user_token", tokensVO)
    }

    override fun getUserTokens(): TokensVO? {
        return Hawk.get("user_token")
    }

    override fun saveUserData(user: User) {
        Hawk.put("user_data", user)
    }

    override fun getUserData(): User? {
        return Hawk.get("user_data")
    }

}