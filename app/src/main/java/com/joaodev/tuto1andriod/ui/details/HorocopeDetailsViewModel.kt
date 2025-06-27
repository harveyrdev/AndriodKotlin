package com.joaodev.tuto1andriod.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaodev.tuto1andriod.domain.model.HoroscopeModel
import com.joaodev.tuto1andriod.domain.usecase.GetPrediction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HorocopeDetailsViewModel @Inject constructor(private val getPrediction: GetPrediction) :
    ViewModel() {

    private var _state = MutableStateFlow<HoroscopeDetalState>(HoroscopeDetalState.Loading)
    val state: StateFlow<HoroscopeDetalState> = _state
 lateinit var horoscope:HoroscopeModel
    fun getHoroscope(sign: HoroscopeModel) {

        horoscope=sign
        viewModelScope.launch {
            _state.value = HoroscopeDetalState.Loading
            val result = withContext(Dispatchers.IO) {
                getPrediction(sign.name)
            }

            if (result != null) {
                _state.value = HoroscopeDetalState.Success(result.horoscope, result.sign,horoscope)
            } else {
                _state.value = HoroscopeDetalState.Error("Ha ocurrido un error donde donde")

            }

        }
    }

}