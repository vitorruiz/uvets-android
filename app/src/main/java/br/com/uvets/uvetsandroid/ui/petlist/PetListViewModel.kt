package br.com.uvets.uvetsandroid.ui.petlist

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.business.network.BasicRxRequester
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class PetListViewModel(userRepository: UserRepository, val petRepository: PetRepository) :
    BaseViewModel<BaseNavigator>(userRepository) {

    val mPetListLiveData = MutableLiveData<List<Pet>>()

    fun fetchPets(force: Boolean = false) {
        if (mPetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(mPetListLiveData.value.isNullOrEmpty())

            registerDisposable(
                petRepository.fetchPets()
                    .networkSchedulers()
                    .subscribeWith(object : BasicRxRequester<List<Pet>, BaseNavigator>(this, mNavigator) {
                        override fun onSuccess(t: List<Pet>) {
                            mPetListLiveData.postValue(t)
                        }
                    })
            )
        }
    }
}