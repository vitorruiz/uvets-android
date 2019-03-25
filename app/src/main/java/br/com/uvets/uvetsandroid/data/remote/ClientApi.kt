package br.com.uvets.uvetsandroid.data.remote

import android.util.Log
import br.com.uvets.uvetsandroid.BuildConfig
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
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    fun getClientWithAuth(c: Class<T>, token: String): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClientAuth(token).build())
            .baseUrl(BuildConfig.BASE_URL)
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

    class Builder<T> {
        private var auth: Boolean = false
        private var userToken: String? = null

        fun withAuth(token: String): Builder<T> {
            this.auth = true
            this.userToken = token
            return this
        }

        fun build(c: Class<T>): T {
            return if (auth) {
                ClientApi<T>().getClientWithAuth(c, userToken ?: "")
            } else {
                ClientApi<T>().getClient(c)
            }
        }
    }
}

class AuthInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val requestBuilder = chain!!.request().newBuilder()
        requestBuilder.addHeader("Authorization", token)
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code() == 401 || response.code() == 403) {
            Log.e("UVETS", "Error API KEY")
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

fun <T> getApiBuilder(): ClientApi.Builder<T> {
    return ClientApi.Builder()
}

fun getPetService(token: String? = null): PetService {
    return if (token != null) {
        getApiBuilder<PetService>()
            .withAuth(token)
            .build(PetService::class.java)
    } else {
        getApiBuilder<PetService>()
            .build(PetService::class.java)
    }
}

fun getAuthService(token: String? = null): AuthService {
    return if (token != null) {
        getApiBuilder<AuthService>()
            .withAuth(token)
            .build(AuthService::class.java)
    } else {
        getApiBuilder<AuthService>()
            .build(AuthService::class.java)
    }
}

fun getUserService(token: String? = null): UserService {
    return if (token != null) {
        getApiBuilder<UserService>()
            .withAuth(token)
            .build(UserService::class.java)
    } else {
        getApiBuilder<UserService>()
            .build(UserService::class.java)
    }
}

fun getVetService(token: String? = null): VetService {
    return if (token != null) {
        getApiBuilder<VetService>()
            .withAuth(token)
            .build(VetService::class.java)
    } else {
        getApiBuilder<VetService>()
            .build(VetService::class.java)
    }
}