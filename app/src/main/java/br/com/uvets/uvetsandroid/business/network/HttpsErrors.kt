package br.com.uvets.uvetsandroid.business.network

import com.google.gson.annotations.SerializedName
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class NetworkError(val code: Int, val msg: String, val body: HttpErrorBody?) : Throwable("$code: $msg")

class BadGateway(body: HttpErrorBody?) : NetworkError(502, "Bad Gateway", body)
class BadRequest(body: HttpErrorBody?) : NetworkError(400, "Bad Request", body)
class Forbidden(body: HttpErrorBody?) : NetworkError(403, "Forbidden", body)
class InternalServerError(body: HttpErrorBody?) : NetworkError(500, "Internal Server NetworkError", body)
class MethodNotAllowed(body: HttpErrorBody?) : NetworkError(405, "Method Not Allowed", body)
class NotFound(body: HttpErrorBody?) : NetworkError(404, "Not Found", body)
class ServiceUnavailable : NetworkError(503, "Service Unavailable", null)
class Unauthorized : NetworkError(401, "Unauthorized", null)

class HttpErrorHandler {
    companion object {
        fun resolveError(error: Throwable, onFail: (HttpErrorBody) -> Unit, onError: (RestError) -> Unit) {
            try {
                if (error is NetworkError) {
                    if (error.body != null) {
                        onFail(error.body)
                    } else {
                        onFail(HttpErrorBody(System.currentTimeMillis(), error.code, error.msg, error.msg))
                    }
                } else if (error is UnknownHostException || error is SocketTimeoutException || error is ConnectException) {
                    onError(RestError(error, true))
                } else {
                    onError(RestError(error))
                }
            } catch (e: Exception) {
                onError(RestError((e)))
            }
        }
    }
}

class HttpErrorBody(
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("status")
    val status: Int,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String
)