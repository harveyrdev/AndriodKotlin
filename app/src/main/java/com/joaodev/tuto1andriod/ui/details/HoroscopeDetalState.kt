package com.joaodev.tuto1andriod.ui.details

import com.joaodev.tuto1andriod.domain.model.HoroscopeModel

sealed class HoroscopeDetalState {

    data object  Loading:HoroscopeDetalState()
    data class  Error(val error:String):HoroscopeDetalState()
    data class  Success(val prediction:String  ,val sing:String, val horoscopeModel: HoroscopeModel): HoroscopeDetalState()
}