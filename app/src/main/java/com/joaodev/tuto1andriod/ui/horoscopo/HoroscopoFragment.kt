package com.joaodev.tuto1andriod.ui.horoscopo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joaodev.tuto1andriod.databinding.FragmentHoroscopoBinding


class HoroscopoFragment : Fragment() {

    private var _binging: FragmentHoroscopoBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentHoroscopoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}