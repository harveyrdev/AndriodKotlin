package com.joaodev.tuto1andriod.data

import android.util.Log
import com.joaodev.tuto1andriod.data.network.HoroscopoApiService
import com.joaodev.tuto1andriod.data.network.response.PredictionResponse
import com.joaodev.tuto1andriod.domain.Repository
import com.joaodev.tuto1andriod.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val apiService: HoroscopoApiService) : Repository {
    override suspend fun getPrediccion(sing: String): PredictionModel? {
        runCatching {
            apiService.getHoroscope(sing)
        }.onSuccess {
            return it.toDomain()
        }.onFailure {
            Log.i("Error", "Ha ocurrido un error ${it.message}")
        }
        return null
    }
}