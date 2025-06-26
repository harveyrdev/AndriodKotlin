package com.joaodev.tuto1andriod.ui.horoscopo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaodev.tuto1andriod.databinding.FragmentHoroscopoBinding
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo.*
import com.joaodev.tuto1andriod.domain.model.HoroscopeModel
import com.joaodev.tuto1andriod.ui.horoscopo.adapter.HorocopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HoroscopoFragment : Fragment() {

    private val horoscopoViewModel by viewModels<HoroscopoViewModel>()
    private var _binging: FragmentHoroscopoBinding? = null
    private val binding get() = _binging!!
    private lateinit var horoscopeadapter: HorocopeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        iniUI()
    }

    private fun iniUI() {
        initRecycler()
        initUiState()
    }

    private fun initRecycler() {
        horoscopeadapter = HorocopeAdapter(onItemselecter = {

            val type = when(it){
                Aquarius -> HoroscopeModel.Aquarius
                Aries -> HoroscopeModel.Aries
                Cancer -> HoroscopeModel.Cancer
                Capricorn -> HoroscopeModel.Capricorn
                Gemini -> HoroscopeModel.Gemini
                Leo -> HoroscopeModel.Leo
                Libra -> HoroscopeModel.Libra
                Pisces -> HoroscopeModel.Pisces
                Sagittarius -> HoroscopeModel.Sagittarius
                Scorpio -> HoroscopeModel.Scorpio
                Taurus -> HoroscopeModel.Taurus
                Virgo -> HoroscopeModel.Virgo
            }

            findNavController().navigate(
                 HoroscopoFragmentDirections.actionHoroscopoFragmentToDetailsActivity(type)
             )
        })
        binding.rvHoroscope.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter=horoscopeadapter
        }

    }

    private fun initUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopoViewModel.horocope.collect {

                    horoscopeadapter.updateList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentHoroscopoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}