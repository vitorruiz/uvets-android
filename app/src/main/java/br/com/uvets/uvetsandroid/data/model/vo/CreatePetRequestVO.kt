package br.com.uvets.uvetsandroid.data.model.vo

import com.google.gson.annotations.SerializedName

class CreatePetRequestVO(
    @SerializedName("name")
    val name: String,
    @SerializedName("birth")
    val birth: Long,
    @SerializedName("race")
    val race: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("photoUrl")
    val photoUrl: String?,
    @SerializedName("castrated")
    val castrated: Boolean,
    @SerializedName("weight")
    val weight: Double?,
    @SerializedName("chipNumber")
    val chipNumber: String?
)