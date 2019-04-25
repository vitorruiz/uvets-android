package br.com.uvets.uvetsandroid.data.repository

import androidx.lifecycle.LiveData
import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.updateSynkStatus
import br.com.uvets.uvetsandroid.utils.Synk
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File


class PetRepository(val configuration: Configuration) {

    fun getPetsLiveData(): LiveData<List<Pet>> {
        return configuration.getStorage().getPets()
    }

    fun fetchPets(): Observable<List<Pet>> {
        return configuration.getApiWithAuth().getPets()
            .updateSynkStatus(key = Synk.Keys.PETS)
            .doOnNext {
                configuration.getStorage().savePets(it)
            }
    }

    fun createPet(pet: Pet): Observable<Pet> {
        return configuration.getApiWithAuth().createPet(pet)
    }

    fun updatePet(pet: Pet): Observable<Pet> {
        return configuration.getApiWithAuth().updatePet(pet.id!!, pet)
    }

    fun deletePet(id: Long): Observable<ResponseBody> {
        return configuration.getApiWithAuth().deletePet(id)
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