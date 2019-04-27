package br.com.uvets.uvetsandroid.ui.vetdetail

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bloder.com.blitzcore.enableWhenUsing
import br.com.concrete.canarinho.formatador.Formatador
import br.com.uvets.uvetsandroid.AppFormValidations
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.ui.MainActivity
import br.com.uvets.uvetsandroid.ui.scheduletreatment.ScheduleTreatmentFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
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
        return inflater.inflate(R.layout.vet_detail_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vet = arguments?.getParcelable(EXTRA_VET) as Vet

        tvVetName.text = vet.name
        tvVetClassification.text = vet.classification
        tvVetAddress.text = vet.address.formatted
        tvVetDescription.text = vet.description

        vet.services.forEach {
            val chip = Chip(cgServices.context).apply {
                text = "${it.name} | ${Formatador.VALOR_COM_SIMBOLO.formata(it.price.toString())}"
                isCheckable = true
                isClickable = true
                tag = it.id
            }
            cgServices.addView(chip)
        }

        btRequestScheduling.setOnClickListener {
            (activity as MainActivity).fragNavController.pushFragment(ScheduleTreatmentFragment.newInstance(vet.id))
            dialog?.dismiss()
        }

        btRequestScheduling.enableWhenUsing(AppFormValidations()) {
            cgServices.isAnySelected()
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View)

        // bottomSheetBehavior.setPeekHeight(resources.getDimensionPixelSize(R.dimen.spacing_material_16x), true)

        val childLayoutParams = view?.layoutParams
        val displayMetrics = DisplayMetrics()

        requireActivity()
            .windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)

        childLayoutParams?.height = displayMetrics.heightPixels

        view?.layoutParams = childLayoutParams
    }
}