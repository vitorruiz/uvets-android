package br.com.uvets.uvetsandroid.ui.petlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.data.PetRepository
import br.com.uvets.uvetsandroid.data.model.Pet

class PetListViewModel(application: Application) : AndroidViewModel(application) {

    private val mPetRepository = PetRepository()
    val mPetListLiveData = MutableLiveData<List<Pet>>()

    fun fetchPets() {
        mPetRepository.fetchPets({
            mPetListLiveData.postValue(it)
        }, {}, {})
    }
}