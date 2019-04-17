package br.com.uvets.uvetsandroid.ui.vetlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Vet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.vet_detail_dialog_fragment.*

class VetDetailDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val EXTRA_VET = "vet"

        fun newInstance(vet: Vet) = VetDetailDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_VET, vet)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        val lp = WindowManager.LayoutParams()
//        lp.copyFrom(dialog?.window?.attributes)
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
//        dialog?.window?.attributes = lp
        return inflater.inflate(R.layout.vet_detail_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vet = arguments?.getParcelable(EXTRA_VET) as Vet

        tvVetName.text = vet.name
        tvVetClassification.text = vet.classification
        tvVetAddress.text = vet.address.formatted
    }
}