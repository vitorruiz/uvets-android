package br.com.uvets.uvetsandroid.ui.createpet


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.enableImagePickerOnClick
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import br.com.uvets.uvetsandroid.utils.PickerUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_create_pet.*
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

class CreatePetFragment : BaseFragment(), CreatePetNavigator {

    private var mPet: Pet? = null
    private var mSelectedPetPhoto: File? = null
    private lateinit var mViewModel: CreatePetViewModel

    companion object {

        const val PET_EXTRA = "pet_extra"

        fun updateInstance(pet: Pet): CreatePetFragment {
            return CreatePetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PET_EXTRA, pet)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_pet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CreatePetViewModel::class.java)
        mViewModel.attachNavigator(this)

        mPet = arguments?.getParcelable(PET_EXTRA)

        setUpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            EasyImage.handleActivityResult(requestCode, resultCode, data!!, activity, object : EasyImage.Callbacks {
                override fun onImagesPicked(imageFiles: MutableList<File>, source: EasyImage.ImageSource?, type: Int) {
                    Log.d(CreatePetFragment::class.java.simpleName, "onImagesPicked")
                    Picasso.get().load(imageFiles[0]).into(ivPetPhoto)
                    mSelectedPetPhoto = imageFiles[0]
                }

                override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                    Log.d(CreatePetFragment::class.java.simpleName, "onImagePickerError")
                }

                override fun onCanceled(source: EasyImage.ImageSource?, type: Int) {
                    Log.d(CreatePetFragment::class.java.simpleName, "onCanceled")
                }

            })
        }

    }

    private fun setUpView() {
        if (mPet == null) {
            activity?.title = "Cadastrar Pet"
        } else {
            activity?.title = "Editar Pet"
            btCreatePet.text = "Salvar"
        }

        mPet?.let {
            tvPetName.setText(it.name)
            tvPetRace.setText(it.race)
            tvPetBirth.setText(DateFormat.getDateFormat(context).format(it.birth))
            tvWeight.setText(it.weight.toString())
            if (it.gender == "Macho") rdMale.isChecked = true else rdFemale.isChecked = true
            cbCastrated.isChecked = it.castrated
            it.photoUrl?.let { photoUrl ->
                Picasso.get().load(photoUrl).into(ivPetPhoto)
            }
        }

        tvPetBirth.setOnClickListener {
            PickerUtils.showDatePickerDialog(context!!, it as TextView, true)
        }

        ivPetPhoto.enableImagePickerOnClick(this)

        btCreatePet.setOnClickListener {

            val checkedRadioButtonId = rgSex.checkedRadioButtonId
            if (checkedRadioButtonId > 0) {
                val gender = if (checkedRadioButtonId == R.id.rdMale) "Macho" else "Fêmea"

                if (isFormValid()) {
                    mViewModel.savePet(
                        mPet?.id,
                        tvPetName.text.toString(),
                        DateFormat.getDateFormat(context).parse(tvPetBirth.text.toString()).time,
                        tvPetRace.text.toString(),
                        gender,
                        null,
                        cbCastrated.isChecked,
                        tvWeight.text.toString().toDouble(),
                        null,
                        mSelectedPetPhoto
                    )
                }
            } else {
                showError("Selecione um gênero")
            }
        }
    }

    override fun onPetSaved(pet: Pet) {
        val i = Intent()
        i.putExtra("pet", pet)
        activity?.setResult(Activity.RESULT_OK, i)
        activity?.finish()
    }

    private fun isFormValid(): Boolean {
        var result = true

        if (tvPetName.text.isNullOrEmpty()) {
            tiPetName.error = "Campo obrigatório"
            result = false
        } else {
            tiPetName.isErrorEnabled = false
        }

        if (tvPetRace.text.isNullOrEmpty()) {
            tiPetRace.error = "Campo obrigatório"
            result = false
        } else {
            tiPetRace.isErrorEnabled = false
        }

        if (tvPetBirth.text.isNullOrEmpty()) {
            tiPetBirth.error = "Campo obrigatório"
            result = false
        } else {
            tiPetBirth.isErrorEnabled = false
        }

        if (tvWeight.text.isNullOrEmpty()) {
            tiWeight.error = "Campo obrigatório"
            result = false
        } else {
            tiWeight.isErrorEnabled = false
        }


        return result
    }
}
