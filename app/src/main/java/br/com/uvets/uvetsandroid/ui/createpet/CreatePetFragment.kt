package br.com.uvets.uvetsandroid.ui.createpet


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
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
import kotlinx.android.synthetic.main.fragment_create_pet.*

class CreatePetFragment : BaseFragment(), CreatePetNavigator {

    private lateinit var mViewModel: CreatePetViewModel

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

        setUpView()
    }

    private fun setUpView() {
        activity?.title = "Cadastrar Pet"

        tvPetBirth.setOnClickListener {
            PickerUtils.showDatePickerDialog(context!!, it as TextView, true)
        }

        ivPetPhoto.enableImagePickerOnClick(this)

        btCreatePet.setOnClickListener {
            var gender = ""

            val checkedRadioButtonId = rgSex.checkedRadioButtonId
            if (checkedRadioButtonId > 0) {
                gender = if (checkedRadioButtonId == R.id.rdMale) "Macho" else "Fêmea"

                if (isFormValid()) {
                    mViewModel.createPet(
                        tvPetName.text.toString(),
                        DateFormat.getDateFormat(context).parse(tvPetBirth.text.toString()).time,
                        tvPetRace.text.toString(),
                        gender,
                        null,
                        cbCastrated.isSelected,
                        tvWeight.text.toString().toDouble(),
                        null
                    )
                }
            } else {
                showError("Selecione um gênero")
            }
        }
    }

    override fun onPetCreated(pet: Pet) {
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
