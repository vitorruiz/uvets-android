package br.com.uvets.uvetsandroid.business.interfaces

import androidx.lifecycle.LiveData
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import io.reactivex.Completable

interface Storage {
    fun clearStorage(): Completable
    fun saveUserTokens(tokensVO: TokensVO)
    fun getUserTokens(): TokensVO?
    fun saveUserData(user: User)
    fun getUserData(): User?
    fun saveDeviceId(deviceId: String)
    fun getDeviceId(): String?

    fun savePets(pets: List<Pet>)
    fun getPets(): LiveData<List<Pet>>
    fun saveVets(vets: List<Vet>)
    fun getVets(): LiveData<List<Vet>>
}