package br.com.uvets.uvetsandroid.business.network

import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

abstract class BasicRxRequester<T, N : BaseNavigator>(val viewModel: BaseViewModel<N>, val navigator: N?) :
    RxRequester<T>() {
    override fun onSuccess(t: T) {

    }

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