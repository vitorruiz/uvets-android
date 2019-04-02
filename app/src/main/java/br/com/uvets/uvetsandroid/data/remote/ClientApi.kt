package br.com.uvets.uvetsandroid.data.remote

import android.util.Log
import br.com.uvets.uvetsandroid.BuildConfig
import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
            .addConverterFactory(GsonConverterFactory.create(getGson()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofit.create(c)
    }

    fun getClientWithAuth(c: Class<T>, storage: Storage): T {
        val retrofit = Retrofit.Builder()
            .client(getOkhttpClientAuth(storage).build())
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(getGson()))
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

    private fun getOkhttpClientAuth(storage: Storage): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .authenticator(TokenAuthenticator(storage))
            .addInterceptor(AuthInterceptor(storage.getUserTokens()?.accessToken!!))
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
    }

    private fun getGson(): Gson {
        return GsonBuilder().apply {
            setDateFormat("yyyy-MM-dd")
            setPrettyPrinting()
        }.create()
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

abstract class RestResponseFactory<T, N : BaseNavigator>(val navigator: N?, val viewModel: BaseViewModel<N>) :
    RestResponseListener<T> {

    override fun onSuccess(obj: T) {}

    override fun onFail(responseCode: Int) {
        if (responseCode == 401) {
            viewModel.doLogout()
        }
        navigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
    }

    override fun onError(throwable: Throwable) {
        navigator?.showError(throwable.localizedMessage)
    }

    override fun onComplete() {
        navigator?.showLoader(false)
    }

}

fun getApiService(storage: Storage? = null): ApiService {
    return if (storage != null) {
        ClientApi<ApiService>().getClientWithAuth(ApiService::class.java, storage)
    } else {
        ClientApi<ApiService>().getClient(ApiService::class.java)
    }
}