package br.com.uvets.uvetsandroid.business.network

import io.reactivex.observers.DisposableObserver
import retrofit2.Response

abstract class RxRequester<T> : DisposableObserver<Response<T>>() {

    abstract fun onSuccess(t: T)
    abstract fun onError(restError: RestError)
    abstract fun onFail(responseCode: Int)
    abstract fun onFinish()

    override fun onNext(t: Response<T>) {
        if (t.isSuccessful) {
            t.body()?.let {
                onSuccess(it)
            }
        } else {
            onFail(t.code())
        }
    }

    override fun onError(e: Throwable) {
        onError(RestError(e.localizedMessage))
        onFinish()
    }

    override fun onComplete() {
        onFinish()
    }
}