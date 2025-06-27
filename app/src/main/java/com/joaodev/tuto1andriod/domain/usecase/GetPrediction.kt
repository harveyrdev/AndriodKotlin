package com.joaodev.tuto1andriod.domain.usecase

import com.joaodev.tuto1andriod.domain.Repository
import javax.inject.Inject

class GetPrediction @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(sign: String) = repository.getPrediccion(sign)

}