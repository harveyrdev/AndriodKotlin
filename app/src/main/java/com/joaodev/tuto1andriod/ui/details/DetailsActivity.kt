package com.joaodev.tuto1andriod.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.joaodev.tuto1andriod.R
import com.joaodev.tuto1andriod.databinding.ActivityDetailsBinding
import com.joaodev.tuto1andriod.domain.model.HoroscopeModel
import com.joaodev.tuto1andriod.domain.model.HoroscopeModel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val horoscopeDetailsViewModel: HorocopeDetailsViewModel by viewModels()
    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()

        horoscopeDetailsViewModel.getHoroscope(args.type)
    }

    private fun initUi() {
        iniListeners()
        initUiState()

    }

    private fun iniListeners() {
       binding.ivBack.setOnClickListener{ onBackPressedDispatcher.onBackPressed()}
    }

    private fun initUiState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                horoscopeDetailsViewModel.state.collect {
                    when (it) {
                        is HoroscopeDetalState.Error -> errorState()


                        HoroscopeDetalState.Loading ->
                            loadingState()


                        is HoroscopeDetalState.Success -> succesState(it)


                    }
                }
            }
        }
    }

    private fun errorState() {
        binding.pb.isVisible = false
    }

    private fun succesState(state: HoroscopeDetalState.Success) {
        binding.pb.isVisible = false
        binding.tvTitle.text = state.sing
        binding.tvBody.text = state.prediction

      val image =  when(state.horoscopeModel){
            Aries -> R.drawable.detail_aries
            Taurus -> R.drawable.detail_taurus
            Gemini -> R.drawable.detail_gemini
            Cancer -> R.drawable.detail_cancer
            Leo -> R.drawable.detail_leo
            Virgo -> R.drawable.detail_virgo
            Libra -> R.drawable.detail_libra
            Scorpio -> R.drawable.detail_scorpio
            Sagittarius -> R.drawable.detail_sagittarius
            Capricorn -> R.drawable.detail_capricorn
            Aquarius -> R.drawable.detail_aquarius
            Pisces -> R.drawable.detail_pisces
        }

        binding.ivDetail.setImageResource(image)

    }

    private fun loadingState() {
        binding.pb.isVisible = true

    }
}