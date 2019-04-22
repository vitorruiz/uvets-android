package br.com.uvets.uvetsandroid.data.remote

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.model.User
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.model.vo.*
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth Calls
    @POST("/login")
    fun auth(@Body loginVO: LoginRequestVO): Observable<TokensVO>

    // User Calls
    @PUT("/users/registerDevice/{deviceId}")
    fun registerDevice(@Path("deviceId") deviceId: String): Observable<ResponseBody>

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

    @DELETE("/pets/{id}")
    fun deletePet(@Path("id") id: Long): Observable<ResponseBody>

    @Multipart
    @POST("pets/{id}/photo")
    fun uploadPetPhoto(@Path("id") id: Long, @Part file: MultipartBody.Part): Deferred<Response<Void>>

    // Vet Calls
    @GET("/vets")
    fun getVets(): Observable<List<Vet>>

    @GET("/vets/{id}/schedule")
    fun getAvailableSchedule(@Path("id") id: Long, @Query("date") date: String?): Observable<VetScheduleVO>

    @POST("/vets/{id}/schedule")
    fun scheduleTreatment(@Path("id") id: Long, @Body treatmentVO: ScheduleTreatmentVO): Observable<TreatmentVO>
}