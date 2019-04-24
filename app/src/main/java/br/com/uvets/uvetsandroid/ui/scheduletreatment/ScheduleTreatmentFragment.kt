package br.com.uvets.uvetsandroid.ui.scheduletreatment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import bloder.com.blitzcore.enableWhenUsing
import br.com.uvets.uvetsandroid.*
import br.com.uvets.uvetsandroid.data.model.vo.TreatmentVO
import br.com.uvets.uvetsandroid.ui.base.BaseFragment
import br.com.uvets.uvetsandroid.utils.ViewAnimation
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.schedule_treatment_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class ScheduleTreatmentFragment : BaseFragment(), ScheduleTreatmentNavigator {

    companion object {
        private const val EXTRA_VET_ID = "vet_id"

        fun newInstance(id: Long) = ScheduleTreatmentFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_VET_ID, id)
            }
        }
    }

    private val mViewModel: ScheduleTreatmentViewModel by viewModel()
    private val mEmptyMessageLiveData = MutableLiveData<String>()
    private lateinit var mSelectedDate: String
    private lateinit var mSelectedTime: String
    private var mVetId: Long = 0
    private var mSelectedPetId: Long = 0

    override fun getLayoutResource(): Int {
        return R.layout.schedule_treatment_fragment
    }

    override fun getTile(): String? {
        return getString(R.string.title_schedule_treatment)
    }

    override fun getEmptyLiveMessage(): LiveData<String>? {
        return mEmptyMessageLiveData
    }

    override fun getRelatedEmptyView(): View? {
        return cgHours
    }

    override fun initComponents(rootView: View, savedInstanceState: Bundle?) {
        mViewModel.attachNavigator(this)
        mVetId = arguments?.getLong(EXTRA_VET_ID) ?: return

        setupPetsGroup()
        setupCalendarView()

        mViewModel.fetchSchedule(mVetId, Date())

        mViewModel.vetScheduleLiveData.observe(this, Observer {
            cgHours.clearCheck()
            cgHours.removeAllViews()

            it.hours.forEach { hour ->
                val chip = Chip(cgHours.context).apply {
                    text = hour
                    isCheckable = true
                    isClickable = true
                }
                cgHours.addView(chip)
            }

            if (it.hours.isEmpty()) {
                mEmptyMessageLiveData.postValue(getString(R.string.schedule_treatment_empty))
                showEmptyView(true)
            } else {
                showEmptyView(false)
            }

            tvSelectedDay.text = it.date.toOnlyDayString()
            tvSelectedMonth.text = it.date.toMonthString()
            mSelectedDate = it.date.toISOString()
        })

        llSelectDay.setOnClickListener {
            if (calendarView.visibility == View.GONE) {
                ViewAnimation.showIn(calendarView)
            }
        }

        btSelectHour.enableWhenUsing(AppFormValidations(), {
            cgHours.isAnySelected()
            cgPets.isAnySelected()
        })

        cgHours.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                mSelectedTime = cgHours.findViewById<Chip>(checkedId).text.toString()
            }
        }

        btSelectHour.setOnClickListener {
            mViewModel.scheduleTreatment(mVetId, mSelectedPetId, mSelectedDate, mSelectedTime, null)
        }

    }

    private fun setupCalendarView() {
        calendarView.minDate = System.currentTimeMillis() - 1000

        ViewAnimation.showOut(calendarView)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            mViewModel.fetchSchedule(mVetId, GregorianCalendar(year, month, dayOfMonth).time)
            ViewAnimation.showOut(view)
        }
    }

    private fun setupPetsGroup() {
        mViewModel.pets.observe(this, Observer {
            it.forEach {
                val chip = Chip(cgPets.context).apply {
                    text = it.name
                    isCheckable = true
                    isClickable = true
                    tag = it.id!!
                }
                cgPets.addView(chip)
            }
        })

        cgPets.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                mSelectedPetId = cgPets.findViewById<Chip>(checkedId).tag as Long
            }
        }
    }

    override fun onScheduleSuccess(treatmentVO: TreatmentVO) {
        showSuccess(R.string.message_successful_scheduling)
        getNavigationController()?.popFragmentWithAnim()
    }
}