package br.com.uvets.uvetsandroid.business

import br.com.uvets.uvetsandroid.business.interfaces.FeatureFlagging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class FirebaseFeatureFlagging(private val remoteConfig: FirebaseRemoteConfig) : FeatureFlagging {

    private val TAG = FirebaseFeatureFlagging::class.java.simpleName

//    init {
//        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                AppLogger.d("$TAG > RemoteConfig fetch and activated successfully!")
//            } else {
//                AppLogger.d("$TAG > RemoteConfig fetch failed!")
//            }
//        }
//    }

    override val mapFeatureEnabled: Boolean
        get() = remoteConfig.getBoolean("map_feature")
}