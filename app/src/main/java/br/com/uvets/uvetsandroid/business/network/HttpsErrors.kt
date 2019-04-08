package br.com.uvets.uvetsandroid.business.network

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class NetworkError(val code: Int, val msg: String) : Throwable("$code: $msg")

object BadGateway : NetworkError(502, "Bad Gateway")
object BadRequest : NetworkError(400, "Bad Request")
object Forbidden : NetworkError(403, "Forbidden")
object InternalServerError : NetworkError(500, "Internal Server NetworkError")
object MethodNotAllowed : NetworkError(405, "Method Not Allowed")
object NotFound : NetworkError(404, "Not Found")
object ServiceUnavailable : NetworkError(503, "Service Unavailable")
object Unauthorized : NetworkError(401, "Unauthorized")

class HttpErrorHandler {
    companion object {
        fun resolveError(error: Throwable, onFail: (Int) -> Unit, onError: (RestError) -> Unit) {
            try {
                if (error is NetworkError) {
                    onFail(error.code)
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