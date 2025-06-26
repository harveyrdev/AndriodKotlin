package com.joaodev.tuto1andriod.ui.horoscopo.adapter

import android.content.Context
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.joaodev.tuto1andriod.databinding.ItemHoroscopeBinding
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo

class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemHoroscopeBinding.bind(view)
    fun render(horoscopeInfo: HoroscopeInfo, onItemselecter: (HoroscopeInfo) -> Unit) {

        val context: Context = binding.txtNameHorocope.context

        binding.ivHoroscope.setImageResource(horoscopeInfo.img)
        binding.txtNameHorocope.text = context.getString(horoscopeInfo.name)


        binding.parent.setOnClickListener {
            startRotationAnimation(binding.ivHoroscope, newLamba = {
                onItemselecter(horoscopeInfo)
            })
//      onItemselecter(horoscopeInfo)
        }


    }

    private fun startRotationAnimation(view: View, newLamba: () -> Unit) {
        view.animate().apply {
            duration = 500
            interpolator = LinearInterpolator()
            rotationBy(360f)
            withEndAction {
                newLamba()
            }
            start()

        }
    }
}