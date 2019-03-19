package br.com.uvets.uvetsandroid.data.prefs

import br.com.uvets.uvetsandroid.data.model.User
import com.orhanobut.hawk.Hawk

class PrefsDataStore {

    companion object {

        fun clear() {
            Hawk.deleteAll()
        }

        fun saveUserToken(token: String) {
            Hawk.put("user_token", token)
        }

        fun getUserToken(): String {
            return Hawk.get("user_token", "")
        }

        fun saveUserData(user: User) {
            Hawk.put("user_data", user)
        }

        fun getUserData(): User {
            return Hawk.get("user_data")
        }
    }
}