package br.com.uvets.uvetsandroid.ui.scheduletreatment

import br.com.uvets.uvetsandroid.data.model.vo.TreatmentVO
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator

interface ScheduleTreatmentNavigator : BaseNavigator {
    fun onScheduleSuccess(treatmentVO: TreatmentVO)
}