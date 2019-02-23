package br.com.uvets.uvetsandroid.data

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.getPetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PetRepository {

    fun fetchPets(onSuccess: (List<Pet>?) -> Unit, onFail: (Int) -> Unit, onError: (Throwable) -> Unit) {
        //TODO: Implementar sistema de cache
        GlobalScope.launch(Dispatchers.Default) {
            val request = getPetApi().getPets()
            try {
                val response = request.await()

                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onFail(response.code())
                }
            } catch (e: Throwable) {
                onError(e)
            }
        }
    }
}