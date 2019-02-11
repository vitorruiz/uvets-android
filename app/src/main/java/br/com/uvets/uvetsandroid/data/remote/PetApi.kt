package br.com.uvets.uvetsandroid.data.remote

import br.com.uvets.uvetsandroid.data.model.Pet
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PetApi {

    @GET("/pets")
    fun getPets(): Deferred<Response<List<Pet>>>
}