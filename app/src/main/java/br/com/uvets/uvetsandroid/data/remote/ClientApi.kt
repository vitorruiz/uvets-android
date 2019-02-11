package br.com.uvets.uvetsandroid.data.remote

import android.util.Log
import br.com.uvets.uvetsandroid.ui.petlist.PetAdapter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientApi<T> {

    fun getClient(c: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClient().build())
            .baseUrl("http://private-24263-uvets.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    fun getClientWithAuth(c: Class<T>, token: String): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClientAuth(token).build())
            .baseUrl("http://private-24263-uvets.apiary-mock.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    private fun getOkhttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    private fun getOkhttpClientAuth(token: String): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
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

fun getPetApi(): PetApi {
    return ClientApi<PetApi>().getClient(PetApi::class.java)
}