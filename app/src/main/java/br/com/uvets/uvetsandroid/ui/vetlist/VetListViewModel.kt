package br.com.uvets.uvetsandroid.ui.vetlist

import android.arch.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class VetListViewModel : BaseViewModel<BaseNavigator>() {

    private val vetRepository = VetRepository()
    val vetListLiveData = MutableLiveData<List<Vet>>()

    fun fetchVets() {
        mNavigator?.showLoader(vetListLiveData.value.isNullOrEmpty())
        vetRepository.fetchVets(object : RestResponseListener<List<Vet>?> {

            override fun onSuccess(obj: List<Vet>?) {
                vetListLiveData.postValue(obj)
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showErrorMessage("Erro de requisição. Código $responseCode")
            }

            override fun onError(throwable: Throwable) {
                mNavigator?.showErrorMessage(throwable.localizedMessage)
            }

            override fun onComplete() {
                mNavigator?.showLoader(false)
            }

        })
    }
}