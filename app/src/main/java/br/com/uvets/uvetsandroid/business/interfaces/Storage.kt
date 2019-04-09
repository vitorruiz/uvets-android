package br.com.uvets.uvetsandroid.business.interfaces

import androidx.lifecycle.LiveData
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO

interface Storage {
    fun clearStorage()
    fun saveUserTokens(tokensVO: TokensVO)
    fun getUserTokens(): TokensVO?
    fun saveUserData(user: User)
    fun getUserData(): User?

    fun savePets(pets: List<Pet>)
    fun getPets(): LiveData<List<Pet>>
}