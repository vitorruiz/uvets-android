package br.com.uvets.uvetsandroid.ui.vetlist

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class VetListViewModel(userRepository: UserRepository, val vetRepository: VetRepository) :
    BaseViewModel<BaseNavigator>(userRepository) {

    val vetListLiveData = MutableLiveData<List<Vet>>()

    fun fetchVets(force: Boolean = false) {
        if (vetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(vetListLiveData.value.isNullOrEmpty())

            registerDisposable(
                vetRepository.fetchVets()
                    .networkSchedulers()
                    .subscribeWith(object : ViewModelRxRequester<List<Vet>, BaseNavigator>(this, mNavigator) {
                        override fun onSuccess(t: List<Vet>) {
                            vetListLiveData.postValue(t)
                        }
                    })
            )
        }
    }
}