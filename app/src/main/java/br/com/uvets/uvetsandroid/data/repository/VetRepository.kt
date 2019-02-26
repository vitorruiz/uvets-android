package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getVetService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VetRepository {

    fun fetchVets(callback: RestResponseListener<List<Vet>?>) {
        GlobalScope.launch(Dispatchers.Default) {
            val request = getVetService().getVets()
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

            callback.onComplete()
        }
    }
}