package com.joaodev.tuto1andriod.domain

import com.joaodev.tuto1andriod.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediccion(sing:String): PredictionModel?
}