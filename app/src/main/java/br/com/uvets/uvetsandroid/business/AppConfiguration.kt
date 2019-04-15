package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.Configuration
import br.com.uvets.uvetsandroid.business.interfaces.Storage
import br.com.uvets.uvetsandroid.data.remote.ApiService
import br.com.uvets.uvetsandroid.data.remote.getApiService

class AppConfiguration(private val mStorage: Storage) : Configuration {

    override fun getApi(): ApiService {
        return getApiService()
    }

    override fun getApiWithAuth(): ApiService {
        return getApiService(mStorage)
    }

    override fun getStorage(): Storage {
        return mStorage
    }

}