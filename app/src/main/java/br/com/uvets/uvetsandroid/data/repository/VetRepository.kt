package br.com.uvets.uvetsandroid.data.repository

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.data.model.Vet
import io.reactivex.Observable

class VetRepository(val configuration: Configuration) {

    fun fetchVets(): Observable<List<Vet>> {
        return configuration.getApiWithAuth().getVets()
    }
}