package br.com.uvets.uvetsandroid.ui.createpet

import android.app.Application
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class CreatePetViewModel(application: Application) : BaseViewModel<CreatePetNavigator>(application) {

    private val mPetRepository = PetRepository()

    fun createPet(
        name: String,
        birth: Long,
        race: String,
        gender: String,
        photoUrl: String?,
        castrated: Boolean,
        weight: Double?,
        chipNumber: String?
    ) {
        val pet = Pet(null, name, birth, race, gender, photoUrl, castrated, weight, chipNumber)
        mNavigator?.showLoader(true)
        mPetRepository.createPet(pet, object : RestResponseListener<Pet> {
            override fun onSuccess(obj: Pet) {
                mNavigator?.onPetCreated(obj)
            }

            override fun onFail(responseCode: Int) {
                mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
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