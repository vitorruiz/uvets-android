package br.com.uvets.uvetsandroid.ui.vetlist

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class VetListViewModel(userRepository: UserRepository, val vetRepository: VetRepository) :
    BaseViewModel<BaseNavigator>(userRepository) {

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
}