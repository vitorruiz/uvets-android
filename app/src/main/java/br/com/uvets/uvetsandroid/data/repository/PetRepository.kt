package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.utils.AppLogger
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class PetRepository(val configuration: Configuration) {

    fun fetchPets(): Observable<Response<List<Pet>>> {
        return Observable.concatArray(
            getPetsFromDb(),
            getPetsFromApi()
        )
    }

    private fun getPetsFromDb(): Observable<Response<List<Pet>>>? {
        return configuration.getStorage().getPets()
            .filter { it.isNotEmpty() }
            .map { Response.success(it) }
            .toObservable()
            .doOnNext {
                AppLogger.d("Dispatching ${it.body()?.size} pets from DB...")
            }
    }

    private fun getPetsFromApi(): Observable<Response<List<Pet>>> {
        return configuration.getApiWithAuth().getPets()
            .doOnNext {
                AppLogger.d("Dispatching ${it.body()?.size} users from API...")
                storePetsInDb(it.body()!!)
            }
    }

    private fun storePetsInDb(pets: List<Pet>) {
        Observable.fromCallable { configuration.getStorage().savePets(pets) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                AppLogger.d("Inserted ${pets.size} users from API in DB...")
            }
    }

    fun createPet(pet: Pet): Observable<Response<Pet>> {
        return configuration.getApiWithAuth().createPet(pet)
    }

    fun updatePet(pet: Pet): Observable<Response<Pet>> {
        return configuration.getApiWithAuth().updatePet(pet.id!!, pet)
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