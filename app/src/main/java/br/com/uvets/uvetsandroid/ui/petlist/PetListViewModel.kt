package br.com.uvets.uvetsandroid.ui.petlist

import br.com.uvets.uvetsandroid.business.network.SimpleRxRequester
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import okhttp3.ResponseBody
import org.koin.core.inject

class PetListViewModel : BaseViewModel<BaseNavigator>() {

    private val petRepository: PetRepository by inject()

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

    fun deletePet(id: Long) {
        mNavigator?.showLoader(true)

        registerDisposable(
            petRepository.deletePet(id)
                .networkSchedulers()
                .subscribeWith(object : ViewModelRxRequester<ResponseBody, BaseNavigator>(this, mNavigator) {
                    override fun onSuccess(t: ResponseBody) {
                        mNavigator?.showSuccess("Pet excluiído com sucesso!")
                    }
                })
        )
    }
}