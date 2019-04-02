package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class PetRepository(val configuration: Configuration) {

    fun fetchPets(callback: RestResponseListener<List<Pet>?>) {
        //TODO: Implementar sistema de cache
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApiWithAuth().getPets()
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
            val request = configuration.getApiWithAuth().createPet(pet)
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

    fun updatePet(pet: Pet, callback: RestResponseListener<Pet>) {
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApiWithAuth().updatePet(pet.id!!, pet)
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

    fun uploadPhoto(petId: Long, file: File, callback: RestResponseListener<String>) {
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        val part = MultipartBody.Part.createFormData("file", file.name, fileReqBody)
        GlobalScope.launch(Dispatchers.Main) {
            val request = configuration.getApiWithAuth().uploadPetPhoto(petId, part)

            try {
                val response = withContext(Dispatchers.IO) { request.await() }

                if (response.isSuccessful) {
                    callback.onSuccess(response.headers()["Location"]!!)
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