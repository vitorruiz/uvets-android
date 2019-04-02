package br.com.uvets.uvetsandroid.ui.createpet

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseFactory
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import java.io.File
import java.util.*

class CreatePetViewModel(userRepository: UserRepository, val petRepository: PetRepository) :
    BaseViewModel<CreatePetNavigator>(userRepository) {

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

        val listener = object : RestResponseFactory<Pet, CreatePetNavigator>(mNavigator, this) {
            override fun onSuccess(obj: Pet) {
                if (photoToUpload != null) {
                    uploadPhoto(obj.id!!, photoToUpload) { photoUrl ->
                        obj.photoUrl = photoUrl
                        mNavigator?.onPetSaved(obj)
                    }
                } else {
                    mNavigator?.onPetSaved(obj)
                }
            }

            override fun onComplete() {}
        }

        if (id == null) {
            petRepository.createPet(pet, listener)
        } else {
            petRepository.updatePet(pet, listener)
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