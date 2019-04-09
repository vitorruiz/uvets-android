package br.com.uvets.uvetsandroid.ui.petlist

import br.com.uvets.uvetsandroid.business.network.SimpleRxRequester
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class PetListViewModel(userRepository: UserRepository, val petRepository: PetRepository) :
    BaseViewModel<BaseNavigator>(userRepository) {

    val mPetListLiveData = petRepository.getPetsLiveData()

    fun fetchPets(force: Boolean = false) {
        if (mPetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(mPetListLiveData.value.isNullOrEmpty())

            registerDisposable(
                petRepository.fetchPets()
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