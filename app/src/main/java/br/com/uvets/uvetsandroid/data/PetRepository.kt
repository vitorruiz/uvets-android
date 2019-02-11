package br.com.uvets.uvetsandroid.data

import android.util.Log
import br.com.uvets.uvetsandroid.data.remote.getPetApi
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PetRepository {

    fun getPets() {

        GlobalScope.launch(Dispatchers.Default) {
            val request = getPetApi().getPets()
            try {
                val response = request.await()

                if (response.isSuccessful) {
                    Log.d("PetRespository", Gson().toJson(response.body()))
                }
                else {
                    Log.d("PetRespository", response.message())
                }
                // Do something with the response.body()
            } catch (e: HttpException) {
                Log.d("PetRespository", e.message())
//                toast(e.code())
            } catch (e: Throwable) {
                Log.d("PetRespository", e.message)
//                toast("Ooops: Something else went wrong")
            }
        }
    }
}