package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getPetService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PetRepository {

    fun fetchPets(callback: RestResponseListener<List<Pet>?>) {
        //TODO: Implementar sistema de cache
        GlobalScope.launch(Dispatchers.Default) {
            val request = getPetService().getPets()
            try {
                val response = request.await()

                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                } else {
                    callback.onFail(response.code())
                }
            } catch (e: Throwable) {
                callback.onError(e)
            }
        }

        callback.onComplete()
    }
}