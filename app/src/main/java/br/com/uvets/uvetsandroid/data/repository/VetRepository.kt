package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.remote.getVetService
import kotlinx.coroutines.*

class VetRepository {

    fun fetchVets(callback: RestResponseListener<List<Vet>?>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = getVetService().getVets()
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

    fun dispose(){
        GlobalScope.coroutineContext[Job]?.cancel()
    }
}