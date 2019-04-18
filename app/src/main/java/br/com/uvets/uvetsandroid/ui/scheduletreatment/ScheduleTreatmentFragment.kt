package br.com.uvets.uvetsandroid.ui.scheduletreatment

import android.view.View
import androidx.lifecycle.Observer
import br.com.uvets.uvetsandroid.R
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import kotlinx.android.synthetic.main.schedule_treatment_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ScheduleTreatmentFragment : BaseFragment() {

    companion object {
        fun newInstance() = ScheduleTreatmentFragment()
    }

    private val mViewModel: ScheduleTreatmentViewModel by viewModel()

    override fun getLayoutResource(): Int {
        return R.layout.schedule_treatment_fragment
    }

    override fun initComponents(rootView: View) {
        calendarView.date = Date().time
        mViewModel.fetchSchedule(3, Date(calendarView.date))

        mViewModel.vetScheduleLiveData.observe(this, Observer {
            showSuccess(it.treatmentDuration)
        })

        calendarView.setOnDateChangeListener { _, _, _, _ ->
            mViewModel.fetchSchedule(3, Date(calendarView.date))
        }
    }

}