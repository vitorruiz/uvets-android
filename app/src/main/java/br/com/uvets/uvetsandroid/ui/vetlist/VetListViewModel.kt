package br.com.uvets.uvetsandroid.ui.vetlist

import br.com.uvets.uvetsandroid.business.network.SimpleRxRequester
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import org.koin.core.inject

class VetListViewModel : BaseViewModel<BaseNavigator>() {

    private val vetRepository: VetRepository by inject()

    val vetListLiveData = vetRepository.getVetsLiveData()

    fun fetchVets(force: Boolean = false) {
        if (vetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(vetListLiveData.value.isNullOrEmpty())

            registerDisposable(
                vetRepository.fetchVets()
                    .networkSchedulers()
                    .subscribeWith(object : SimpleRxRequester() {
                        override fun onFinish() {
                            mNavigator?.showLoader(false)
                        }
                    })
            )
        }
    }
}