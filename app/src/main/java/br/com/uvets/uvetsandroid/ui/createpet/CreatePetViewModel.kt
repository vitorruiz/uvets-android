package br.com.uvets.uvetsandroid.ui.createpet

import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseFactory
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import org.koin.core.inject
import java.io.File
import java.util.*

class CreatePetViewModel : BaseViewModel<CreatePetNavigator>() {

    private val petRepository: PetRepository by inject()

    fun savePet(
        id: Long? = null,
        name: String,
        birth: Long,
        race: String,
        gender: String,
        photoUrl: String?,
        castrated: Boolean,
        weight: Double,
        chipNumber: String?,
        photoToUpload: File?
    ) {

        val pet = Pet(id, name, Date(birth), race, gender, photoUrl, castrated, weight, chipNumber)
        mNavigator?.showLoader(true)

        val listener = object : ViewModelRxRequester<Pet, CreatePetNavigator>(this, mNavigator) {
            override fun onSuccess(t: Pet) {
                if (photoToUpload != null) {
                    uploadPhoto(t.id!!, photoToUpload) { photoUrl ->
                        t.photoUrl = photoUrl
                        mNavigator?.onPetSaved(t)
                    }
                } else {
                    mNavigator?.onPetSaved(t)
                }
            }

            override fun onComplete() {

            }
        }

        if (id == null) {
            registerDisposable(
                petRepository.createPet(pet)
                    .networkSchedulers()
                    .subscribeWith(listener)
            )
        } else {
            registerDisposable(
                petRepository.updatePet(pet)
                    .networkSchedulers()
                    .subscribeWith(listener)
            )
        }
    }

    private fun uploadPhoto(petId: Long, file: File, onSuccess: (String) -> Unit) {
        petRepository.uploadPhoto(
            petId,
            file,
            object : RestResponseFactory<String, CreatePetNavigator>(mNavigator, this) {
                override fun onSuccess(obj: String) {
                    onSuccess(obj)
                }
            })
    }

}