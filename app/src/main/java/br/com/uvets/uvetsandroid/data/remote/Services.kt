package br.com.uvets.uvetsandroid.data.remote

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface AuthService {

    @POST("/login")
    fun auth(@Body loginVO: LoginRequestVO): Deferred<Response<ResponseBody>>

    @POST("/users/tutor")
    fun registerTutor(@Body signUpRequestVO: SignUpRequestVO): Deferred<Response<Void>>
}

interface PetService {

    @GET("/pets")
    fun getPets(): Deferred<Response<List<Pet>>>
}

interface VetService {

    @GET("/vets")
    fun getVets(): Deferred<Response<List<Vet>>>
}