package br.com.uvets.uvetsandroid.business.network

import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import io.reactivex.observers.DisposableObserver

abstract class RxRequester<T> : DisposableObserver<T>() {

    abstract fun onSuccess(t: T)
    abstract fun onError(restError: RestError)
    abstract fun onFail(responseCode: Int)
    abstract fun onFinish()

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        HttpErrorHandler.resolveError(e, {
            onFail(it)
        }, {
            onError(it)
        })
        onFinish()
    }

    override fun onComplete() {
        onFinish()
    }
}

abstract class ViewModelRxRequester<T, N : BaseNavigator>(val viewModel: BaseViewModel<N>, val navigator: N?) :
    RxRequester<T>() {
    override fun onSuccess(t: T) {}

    override fun onError(restError: RestError) {
        navigator?.onRequestError(restError)
    }

    override fun onFail(responseCode: Int) {
        if (responseCode == 401) {
            viewModel.doLogout()
        }
        navigator?.onRequestFail(responseCode)
    }

    override fun onFinish() {
        navigator?.showLoader(false)
    }
}

abstract class SimpleRxRequester : RxRequester<Any>() {
    override fun onSuccess(t: Any) {

    }

    override fun onError(restError: RestError) {

    }

    override fun onFail(responseCode: Int) {

    }

    override fun onFinish() {

    }
}