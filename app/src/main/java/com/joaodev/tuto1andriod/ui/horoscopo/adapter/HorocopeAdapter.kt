package com.joaodev.tuto1andriod.ui.horoscopo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joaodev.tuto1andriod.R
import com.joaodev.tuto1andriod.domain.model.HoroscopeInfo

class HorocopeAdapter(private var horoscopeList: List<HoroscopeInfo> = emptyList(),
    private  val onItemselecter:(HoroscopeInfo)->Unit
    ) :
    RecyclerView.Adapter<HoroscopeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        return HoroscopeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        )
    }

    override fun getItemCount() = horoscopeList.size

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        holder.render(horoscopeList[position],onItemselecter)

    }


    fun updateList (listaHorocope: List<HoroscopeInfo>){
horoscopeList=listaHorocope
        notifyDataSetChanged()

    }


}