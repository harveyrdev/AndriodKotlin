package com.joaodev.tuto1andriod.ui.details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.navArgs
import com.joaodev.tuto1andriod.R
import com.joaodev.tuto1andriod.databinding.ActivityDetailsBinding
import com.joaodev.tuto1andriod.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val horoscopeDetailsViewModel: HorocopeDetailsViewModel by viewModels()
    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        args.type
    }
}