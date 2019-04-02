package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VetRepository(val configuration: Configuration) {

    fun fetchVets(callback: RestResponseListener<List<Vet>?>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApiWithAuth().getVets()
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
}