package br.com.uvets.uvetsandroid.business.network

class RestError(val exception: Throwable, val isConnectionError: Boolean = false)