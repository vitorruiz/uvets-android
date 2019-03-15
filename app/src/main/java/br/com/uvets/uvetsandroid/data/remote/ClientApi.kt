package br.com.uvets.uvetsandroid.data.remote

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientApi<T> {

    fun getClient(c: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClient().build())
            .baseUrl("https://uvets-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    fun getClientWithAuth(c: Class<T>, token: String): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClientAuth(token).build())
            .baseUrl("https://uvets-api.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    private fun getOkhttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    private fun getOkhttpClientAuth(token: String): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }
}

class AuthInterceptor(val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val requestBuilder = chain!!.request().newBuilder()
        requestBuilder.addHeader("Authorization", token)
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code() == 401) {
            Log.e("MEUAPP", "Error API KEY")
        }
        return response
    }
}

interface RestResponseListener<T> {
    fun onSuccess(obj: T)
    fun onFail(responseCode: Int)
    fun onError(throwable: Throwable)
    fun onComplete()
}

fun getPetService(token: String): PetService {
    return ClientApi<PetService>().getClientWithAuth(PetService::class.java, token)
}

fun getAuthService(): AuthService {
    return ClientApi<AuthService>().getClient(AuthService::class.java)
}

fun getVetService(): VetService {
    return ClientApi<VetService>().getClient(VetService::class.java)
}