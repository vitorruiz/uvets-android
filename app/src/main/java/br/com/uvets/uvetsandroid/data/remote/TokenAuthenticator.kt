package br.com.uvets.uvetsandroid.data.remote

import br.com.uvets.uvetsandroid.BuildConfig
import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.model.vo.TokensVO
import br.com.uvets.uvetsandroid.utils.AppLogger
import com.google.gson.Gson
import okhttp3.*

class TokenAuthenticator(private val storage: Storage) : Authenticator {

    private val TAG = TokenAuthenticator::class.java.simpleName

    companion object {
        const val ACCESS_TOKEN_URL = "${BuildConfig.BASE_URL}/auth/refresh_token"
        const val AUTHORIZATION_HEADER = "Authorization"
        const val CONTENT_TYPE_HEADER = "Content-Type"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (getRetryCount(response) >= 3) {
            return null // If we've failed 3 times, give up.
        }

        AppLogger.d("$TAG > Trying to reauthenticate.\nRoute: $route\nResponse: $response")

        val accessToken = getNewAccessToken() ?: return null

        return response.request().newBuilder()
            .header(AUTHORIZATION_HEADER, accessToken)
            .build()
    }

    private fun getNewAccessToken(): String? {
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val authRequest = Request.Builder()
            .url(ACCESS_TOKEN_URL)
            .addHeader(AUTHORIZATION_HEADER, storage.getUserTokens()?.refreshToken ?: "")
            .addHeader(CONTENT_TYPE_HEADER, "application/json")
            .build()
        val authResponse = okHttpClient.newCall(authRequest).execute()
        if (authResponse.code() == 200) {
            val authResponseBody = authResponse.body()?.string()
            val response = Gson().fromJson(authResponseBody, TokensVO::class.java)
            storage.saveUserTokens(response)
            return response.accessToken
        }
        return null
    }

    private fun getRetryCount(response: Response?): Int {
        var tempResponse = response?.priorResponse()
        var result = 1

        while (tempResponse != null) {
            tempResponse = tempResponse.priorResponse()
            result++
        }
        return result
    }


}