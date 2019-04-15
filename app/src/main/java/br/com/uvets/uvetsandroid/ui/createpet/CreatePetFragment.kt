package br.com.uvets.uvetsandroid.ui.createpet


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Pet
import br.com.uvets.uvetsandroid.enableImagePickerOnClick
import br.com.uvets.uvetsandroid.loadFromFile
import br.com.uvets.uvetsandroid.loadFromUrl
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import br.com.uvets.uvetsandroid.utils.AppLogger
import br.com.uvets.uvetsandroid.utils.PickerUtils
import com.esafirm.imagepicker.features.ImagePicker
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.fragment_create_pet.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class CreatePetFragment : BaseFragment(), CreatePetNavigator {

    private var mPet: Pet? = null
    private var mSelectedPetPhoto: File? = null
    private val mViewModel: CreatePetViewModel by viewModel()

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

    override fun getLayoutResource(): Int {
        return R.layout.fragment_create_pet
    }

    override fun initComponents(rootView: View) {
        mViewModel.attachNavigator(this)

        (activity as? AppCompatActivity)?.supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        mPet = arguments?.getParcelable(PET_EXTRA)

        setUpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {
                AppLogger.e("CreatePetFragment > onActivityResult: requestCode $requestCode resultCode $resultCode > data is null")
                return
            }
            if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
                val image = ImagePicker.getFirstImageOrNull(data)
                mSelectedPetPhoto = Compressor(activity).compressToFile(File(image.path))
                ivPetPhoto.loadFromFile(mSelectedPetPhoto!!)
            }
        }

    }

    private fun setUpView() {
        if (mPet == null) {
            setTitle(getString(R.string.button_register_pet))
        } else {
            setTitle(getString(R.string.button_edit_pet))
            btCreatePet.text = getString(R.string.button_save)
        }

        mPet?.let {
            tvPetName.setText(it.name)
            tvPetRace.setText(it.race)
            tvPetBirth.setText(DateFormat.getDateFormat(context).format(it.birth))
            tvWeight.setText(it.weight.toString())
            if (it.gender == "Macho") rdMale.isChecked = true else rdFemale.isChecked = true
            cbCastrated.isChecked = it.castrated
            it.photoUrl?.let { photoUrl ->
                ivPetPhoto.loadFromUrl(photoUrl)
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
                showError(getString(R.string.create_pet_error_select_gender))
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
            tiPetName.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiPetName.isErrorEnabled = false
        }

        if (tvPetRace.text.isNullOrEmpty()) {
            tiPetRace.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiPetRace.isErrorEnabled = false
        }

        if (tvPetBirth.text.isNullOrEmpty()) {
            tiPetBirth.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiPetBirth.isErrorEnabled = false
        }

        if (tvWeight.text.isNullOrEmpty()) {
            tiWeight.error = getString(R.string.error_mandatory_field)
            result = false
        } else {
            tiWeight.isErrorEnabled = false
        }


        return result
    }
}
