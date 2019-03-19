package br.com.uvets.uvetsandroid.data.model.vo

class CreatePetRequestVO(val name: String,
                         val birth: Long,
                         val race: String,
                         val gender: String,
                         val photoUrl: String?,
                         val castrated: Boolean,
                         val weight: Double?,
                         val chipNumber: String?)