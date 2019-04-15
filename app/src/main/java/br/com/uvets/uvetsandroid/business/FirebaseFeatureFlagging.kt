package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.FeatureFlagging
import br.com.uvets.uvetsandroid.utils.AppLogger
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class FirebaseFeatureFlagging(private val remoteConfig: FirebaseRemoteConfig) : FeatureFlagging {

    private val TAG = FirebaseFeatureFlagging::class.java.simpleName

    init {
        remoteConfig.fetch().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                remoteConfig.activateFetched()
                AppLogger.d("$TAG RemoteConfig fetch successful!")
            } else {
                AppLogger.d("$TAG RemoteConfig fetch failed!")
            }
        }
    }

    override val mapFeatureEnabled: Boolean
        get() = remoteConfig.getBoolean("map_feature")
}