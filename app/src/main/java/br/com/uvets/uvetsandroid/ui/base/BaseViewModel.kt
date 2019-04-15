package br.com.uvets.uvetsandroid.ui.base

import androidx.lifecycle.ViewModel
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.utils.AppLogger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

open class BaseViewModel<N : BaseNavigator>(val userRepository: UserRepository) : ViewModel() {
    protected var mNavigator: N? = null

    private val mCompositeDisposable = CompositeDisposable()

    protected fun registerDisposable(observable: DisposableObserver<*>) {
        mCompositeDisposable.add(observable)
    }

    protected fun registerDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    fun attachNavigator(navigator: N) {
        mNavigator = navigator
    }

    fun doLogout() {
        registerDisposable(
            userRepository.logoutUser()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    mNavigator?.onLogoutSucceeded()
                }, {
                    AppLogger.e(it.localizedMessage)
                })
        )
    }

    fun isUserAuthenticated(): Boolean {
        return userRepository.isUserAuthenticated()
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}