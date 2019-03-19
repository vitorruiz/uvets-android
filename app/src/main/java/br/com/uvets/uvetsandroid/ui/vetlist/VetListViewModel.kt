package br.com.uvets.uvetsandroid.ui.vetlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class VetListViewModel(application: Application) : BaseViewModel<BaseNavigator>(application) {

    private val vetRepository = VetRepository()
    val vetListLiveData = MutableLiveData<List<Vet>>()

    fun fetchVets(force: Boolean = false) {
        if (vetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(vetListLiveData.value.isNullOrEmpty())
            vetRepository.fetchVets(object : RestResponseListener<List<Vet>?> {

                override fun onSuccess(obj: List<Vet>?) {
                    vetListLiveData.postValue(obj)
                }

                override fun onFail(responseCode: Int) {
                    mNavigator?.showError("Erro de requisição. Código $responseCode")
                }

                override fun onError(throwable: Throwable) {
                    mNavigator?.showError(throwable.localizedMessage)
                }

                override fun onComplete() {
                    mNavigator?.showLoader(false)
                }

            })
        }
    }

    override fun onCleared() {
        vetRepository.dispose()
        Log.d("Vem Monstro", "onCleared")
        super.onCleared()

    }
}