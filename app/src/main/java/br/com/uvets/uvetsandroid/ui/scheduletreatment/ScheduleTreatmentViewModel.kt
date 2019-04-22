package br.com.uvets.uvetsandroid.ui.scheduletreatment

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.vo.ScheduleTreatmentVO
import br.com.uvets.uvetsandroid.data.model.vo.TreatmentVO
import br.com.uvets.uvetsandroid.data.model.vo.VetScheduleVO
import br.com.uvets.uvetsandroid.data.repository.PetRepository
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.toISOString
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import org.koin.core.inject
import java.util.*

class ScheduleTreatmentViewModel : BaseViewModel<ScheduleTreatmentNavigator>() {

    private val vetRepository: VetRepository by inject()
    private val petRepository: PetRepository by inject()

    val vetScheduleLiveData = MutableLiveData<VetScheduleVO>()
    val pets = petRepository.getPetsLiveData()

    fun fetchSchedule(id: Long, date: Date) {
        mNavigator?.showLoader(true)

        registerDisposable(
            vetRepository.fetchSchedule(id, date.toISOString())
                .networkSchedulers()
                .subscribeWith(object :
                    ViewModelRxRequester<VetScheduleVO, ScheduleTreatmentNavigator>(this, mNavigator) {
                    override fun onSuccess(t: VetScheduleVO) {
                        vetScheduleLiveData.postValue(t)
                    }
                })
        )
    }

    fun scheduleTreatment(vetId: Long, petId: Long, date: String, time: String, observations: String?) {
        val treatmentVO = ScheduleTreatmentVO(petId, date, time, observations)

        mNavigator?.showLoader(true)

        registerDisposable(
            vetRepository.scheduleTreatment(vetId, treatmentVO)
                .networkSchedulers()
                .subscribeWith(object :
                    ViewModelRxRequester<TreatmentVO, ScheduleTreatmentNavigator>(this, mNavigator) {
                    override fun onSuccess(t: TreatmentVO) {
                        mNavigator?.onScheduleSuccess(t)
                    }
                })
        )
    }

}