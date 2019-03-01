package br.com.uvets.uvetsandroid.ui.petlist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class PetListViewModel(application: Application) : BaseViewModel<BaseNavigator>(application) {

    private val mPetRepository = PetRepository()
    val mPetListLiveData = MutableLiveData<List<Pet>>()

    fun fetchPets(force: Boolean = false) {
        if (mPetListLiveData.value.isNullOrEmpty() || force) {
            mNavigator?.showLoader(mPetListLiveData.value.isNullOrEmpty())
            mPetRepository.fetchPets(object : RestResponseListener<List<Pet>?> {
                override fun onSuccess(obj: List<Pet>?) {
                    mPetListLiveData.postValue(obj)
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