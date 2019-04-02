package br.com.uvets.uvetsandroid.ui.createpet

import android.app.Application
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.data.remote.RestResponseListener
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import java.io.File
import java.util.*

class CreatePetViewModel(application: Application) : BaseViewModel<CreatePetNavigator>(application) {

    private val mPetRepository = PetRepository()

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

        if (id == null) {
            mPetRepository.createPet(pet, object : RestResponseListener<Pet> {
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

                override fun onFail(responseCode: Int) {
                    mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
                }

                override fun onError(throwable: Throwable) {
                    mNavigator?.showError(throwable.localizedMessage)
                }

                override fun onComplete() {
                    //mNavigator?.showLoader(false)
                }

            })
        } else {
            mPetRepository.updatePet(pet, object : RestResponseListener<Pet> {
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

                override fun onFail(responseCode: Int) {
                    mNavigator?.showError("Ocorreu um erro na requisição. Código: $responseCode")
                }

                override fun onError(throwable: Throwable) {
                    mNavigator?.showError(throwable.localizedMessage)
                }

                override fun onComplete() {
                    //mNavigator?.showLoader(false)
                }

            })
        }
    }

    private fun uploadPhoto(petId: Long, file: File, onSuccess: (String) -> Unit) {
        mPetRepository.uploadPhoto(petId, file, object : RestResponseListener<String> {
            override fun onSuccess(obj: String) {
                onSuccess(obj)
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