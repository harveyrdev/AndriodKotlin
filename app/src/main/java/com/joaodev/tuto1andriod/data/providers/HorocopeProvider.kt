package com.joaodev.tuto1andriod.data.providers

import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo.*
import javax.inject.Inject

class HorocopeProvider @Inject constructor() {
    fun getHoroscopes(): List<HoroscopeInfo> {
        return listOf(
            Aries,
            Taurus,
            Gemini,
            Cancer,
            Leo,
            Virgo,
            Libra,
            Scorpio,
            Sagittarius,
            Capricorn,
            Aquarius,
            Pisces
        )
    }
}