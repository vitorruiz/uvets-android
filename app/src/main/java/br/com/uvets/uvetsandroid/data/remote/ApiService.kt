package br.com.uvets.uvetsandroid.data.remote

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.model.vo.LoginRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.SignUpRequestVO
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.data.model.vo.VetScheduleVO
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth Calls
    @POST("/login")
    fun auth(@Body loginVO: LoginRequestVO): Observable<TokensVO>

    // User Calls
    @GET("/users/fetch")
    fun fetchUser(): Observable<User>

    @POST("/users")
    fun registerTutor(@Body signUpRequestVO: SignUpRequestVO): Observable<User>

    // Pet Calls
    @GET("/pets")
    fun getPets(): Observable<List<Pet>>

    @POST("/pets")
    fun createPet(@Body pet: Pet): Observable<Pet>

    @PUT("/pets/{id}")
    fun updatePet(@Path("id") id: Long, @Body pet: Pet): Observable<Pet>

    @Multipart
    @POST("pets/{id}/photo")
    fun uploadPetPhoto(@Path("id") id: Long, @Part file: MultipartBody.Part): Deferred<Response<Void>>

    // Vet Calls
    @GET("/vets")
    fun getVets(): Observable<List<Vet>>

    @GET("/vets/{id}/schedule")
    fun getAvailableSchedule(@Path("id") id: Long, @Query("date") date: String?): Observable<VetScheduleVO>

    @POST("/vets/{id}/schedule")
    fun scheduleTreatment(@Path("id") id: Long)
}