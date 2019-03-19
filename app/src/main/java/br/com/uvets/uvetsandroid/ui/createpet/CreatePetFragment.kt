package br.com.uvets.uvetsandroid.ui.createpet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import br.com.uvets.uvetsandroid.R
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
    }

    override fun onPetCreated() {

    }
}
