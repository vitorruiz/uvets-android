package br.com.uvets.uvetsandroid.ui.scheduletreatment

import androidx.lifecycle.MutableLiveData
import br.com.uvets.uvetsandroid.business.network.ViewModelRxRequester
import br.com.uvets.uvetsandroid.data.model.vo.VetScheduleVO
import br.com.uvets.uvetsandroid.data.repository.VetRepository
import br.com.uvets.uvetsandroid.networkSchedulers
import br.com.uvets.uvetsandroid.toISOString
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel
import org.koin.core.inject
import java.util.*

class ScheduleTreatmentViewModel : BaseViewModel<BaseNavigator>() {

    private val vetRepository: VetRepository by inject()

    val vetScheduleLiveData = MutableLiveData<VetScheduleVO>()

    fun fetchSchedule(id: Long, date: Date) {
        registerDisposable(
            vetRepository.fetchSchedule(id, date.toISOString())
                .networkSchedulers()
                .subscribeWith(object : ViewModelRxRequester<VetScheduleVO, BaseNavigator>(this, mNavigator) {
                    override fun onSuccess(t: VetScheduleVO) {
                        vetScheduleLiveData.postValue(t)
                    }
                })
        )
    }

}