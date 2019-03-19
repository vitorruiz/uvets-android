package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.prefs.PrefsDataStore
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getPetService
import kotlinx.coroutines.*

class PetRepository {

    fun fetchPets(callback: RestResponseListener<List<Pet>?>) {
        //TODO: Implementar sistema de cache
        GlobalScope.launch(Dispatchers.Main) {
            val request = getPetService(PrefsDataStore.getUserToken()).getPets()
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }

    fun createPet(pet: Pet, callback: RestResponseListener<Pet>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = getPetService(PrefsDataStore.getUserToken()).createPet(pet)
            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    response.body()?.let { callback.onSuccess(it) }
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }

            callback.onComplete()
        }
    }

    fun dispose(){
        GlobalScope.coroutineContext[Job]?.cancel()
    }
}