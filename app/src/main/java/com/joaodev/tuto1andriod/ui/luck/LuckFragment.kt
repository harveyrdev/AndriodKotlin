package com.joaodev.tuto1andriod.ui.luck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joaodev.tuto1andriod.databinding.FragmentLuckBinding


class LuckFragment : Fragment() {
    private var _binging: FragmentLuckBinding? = null
    private val binding get() = _binging!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}