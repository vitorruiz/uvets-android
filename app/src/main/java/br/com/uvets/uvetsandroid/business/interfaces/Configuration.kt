package br.com.uvets.uvetsandroid.business.interfaces

import br.com.uvets.uvetsandroid.data.remote.ApiService

interface Configuration {
    fun getApi(): ApiService
    fun getApiWithAuth(): ApiService
    fun getStorage(): Storage
}