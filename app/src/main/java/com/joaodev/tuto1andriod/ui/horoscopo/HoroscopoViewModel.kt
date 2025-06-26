package com.joaodev.tuto1andriod.ui.horoscopo

import androidx.lifecycle.ViewModel
import com.joaodev.tuto1andriod.data.providers.HorocopeProvider
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HoroscopoViewModel @Inject constructor( horoscopeProvider: HorocopeProvider):ViewModel() {

    private var _horoscope= MutableStateFlow<List<HoroscopeInfo>> (emptyList())
    val  horocope:StateFlow<List<HoroscopeInfo>> =_horoscope

    init {

        _horoscope.value= horoscopeProvider.getHoroscopes()
    }

}