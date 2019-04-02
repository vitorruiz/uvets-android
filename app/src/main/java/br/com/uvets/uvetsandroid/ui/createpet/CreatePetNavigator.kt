package br.com.uvets.uvetsandroid.ui.createpet

import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator

interface CreatePetNavigator : BaseNavigator {
    fun onPetSaved(pet: Pet)
}