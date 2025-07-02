package com.joaodev.tuto1andriod.ui.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.joaodev.tuto1andriod.R
import com.joaodev.tuto1andriod.databinding.FragmentLuckBinding
import com.joaodev.tuto1andriod.ui.core.listeners.OnSwiteTouckListener
import com.joaodev.tuto1andriod.ui.horoscopo.HoroscopoViewModel
import com.joaodev.tuto1andriod.ui.model.LuckyModel
import com.joaodev.tuto1andriod.ui.providers.RandomCardsProviders
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject


@AndroidEntryPoint
class LuckFragment : Fragment() {
    private val luckViewModel by viewModels<LuckViewModel>()
    private var _binging: FragmentLuckBinding? = null
    private val binding get() = _binging!!

    @Inject
    lateinit var randomCardsProviders: RandomCardsProviders
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentLuckBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initUi() {
        preparePrediccion()
        initListeners()
    }

    private fun preparePrediccion() {
        val luck: LuckyModel? =randomCardsProviders.GetLucky()
         luck?.let { lucks->
             val luckText= getString(lucks.texto);
             binding.tvLucky.text=luckText
             binding.ivLuckCard.setImageResource(lucks.image)
             binding.tvshare.setOnClickListener{ shareResult(luckText)}
         }
    }

    private fun shareResult(predicion:String) {

        val sendIndentL:Intent  = Intent().apply {
            action=Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,predicion)

            type="text/plain"
        }

        val shareIntent= Intent.createChooser(sendIndentL,null)
        startActivity(shareIntent)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
//        binding.ivRulete.setOnClickListener {
//            spinRulete()
//        }

        binding.ivRulete.setOnTouchListener(object : OnSwiteTouckListener (requireContext()){
            override fun onSwipeRight() {
               spinRulete()
            }

            override fun onSwipeLeft() {
                spinRulete()
            }
        })
    }

    private fun spinRulete() {
        val ramdow = Random()
        val degress = ramdow.nextInt(1440) + 360


        var animaros =
            ObjectAnimator.ofFloat(binding.ivRulete, View.ROTATION, 0f, degress.toFloat())

        animaros.duration = 2000
        animaros.interpolator = DecelerateInterpolator()
        animaros.doOnEnd {
            slideCard()
        }

        animaros.start()
    }

    private fun slideCard() {
        val splideUpAnimtion = AnimationUtils.loadAnimation(requireContext(), R.anim.slideup)
        splideUpAnimtion.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                binding.ivReverse.isVisible = true
            }

            override fun onAnimationEnd(animation: Animation?) {
                growCard()
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        binding.ivReverse.startAnimation(splideUpAnimtion)


    }

    private fun growCard() {
        val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.growcard)
        growAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.ivReverse.isVisible=false
                showPremonition()

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.ivReverse.startAnimation(growAnimation)
    }

    private fun showPremonition() {

        val dissperAnimation=AlphaAnimation(1.0f,0.0f)
        dissperAnimation.duration=200

        val apperaAnimation=AlphaAnimation(0.0f,1.0f)
        apperaAnimation.duration=1000

        dissperAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

                binding.preview.isVisible=false
                binding.prediccion.isVisible=true
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })

        binding.preview.startAnimation(dissperAnimation)
        binding.prediccion.startAnimation(apperaAnimation)
    }
}