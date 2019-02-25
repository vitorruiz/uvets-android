package br.com.uvets.uvetsandroid.ui.petlist

import android.arch.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.PetRepository
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class PetListViewModel : BaseViewModel() {

    private val mPetRepository = PetRepository()
    val mPetListLiveData = MutableLiveData<List<Pet>>()

    fun fetchPets() {
        isLoadingLiveData.postValue(true)
        mPetRepository.fetchPets({
            mPetListLiveData.postValue(it)
            isLoadingLiveData.postValue(false)
        }, {
            errorMessageLiveData.postValue("Erro de requisição. Código $it")
            isLoadingLiveData.postValue(false)
        }, {
            errorMessageLiveData.postValue(it.localizedMessage)
            isLoadingLiveData.postValue(false)
        })
    }
}