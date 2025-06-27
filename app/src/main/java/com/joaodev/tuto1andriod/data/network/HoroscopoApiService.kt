package com.joaodev.tuto1andriod.data.network

import com.joaodev.tuto1andriod.data.network.response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopoApiService {
    @GET ("/{sign}")
    suspend fun getHoroscope(@Path("sign") sing:String) :PredictionResponse



}