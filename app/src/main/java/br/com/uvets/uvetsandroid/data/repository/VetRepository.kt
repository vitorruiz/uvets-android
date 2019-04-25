package br.com.uvets.uvetsandroid.data.repository

import androidx.lifecycle.LiveData
import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Vet
import br.com.uvets.uvetsandroid.data.model.vo.ScheduleTreatmentVO
import br.com.uvets.uvetsandroid.data.model.vo.TreatmentVO
import br.com.uvets.uvetsandroid.data.model.vo.VetScheduleVO
import br.com.uvets.uvetsandroid.updateSynkStatus
import br.com.uvets.uvetsandroid.utils.Synk
import io.reactivex.Observable

class VetRepository(val configuration: Configuration) {

    fun getVetsLiveData(): LiveData<List<Vet>> {
        return configuration.getStorage().getVets()
    }

    fun fetchVets(): Observable<List<Vet>> {
        return configuration.getApiWithAuth().getVets()
            .updateSynkStatus(Synk.Keys.VETS)
            .doOnNext {
                configuration.getStorage().saveVets(it)
            }
    }

    fun fetchSchedule(id: Long, date: String): Observable<VetScheduleVO> {
        return configuration.getApiWithAuth().getAvailableSchedule(id, date)
    }

    fun scheduleTreatment(id: Long, treatmentVO: ScheduleTreatmentVO): Observable<TreatmentVO> {
        return configuration.getApiWithAuth().scheduleTreatment(id, treatmentVO)
    }
}