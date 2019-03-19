package br.com.uvets.uvetsandroid.ui.createpet

import android.app.Application
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class CreatePetViewModel(application: Application): BaseViewModel<CreatePetNavigator>(application) {

    fun createPet(name: String, birth: Long, race: String, gender: String, photoUrl: String?, castrated: Boolean, weight: Double?, chipNumber: String?) {

    }
}