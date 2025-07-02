package com.joaodev.tuto1andriod.ui.palmistry

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.joaodev.tuto1andriod.databinding.FragmentPalmistryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PalmistryFragment : Fragment() {

    companion object{
        private  const val  cameraPermison= Manifest.permission.CAMERA
    }
    private var _binging: FragmentPalmistryBinding? = null
    private val binding get() = _binging!!
    private  val requestPermissionLauncher=registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted ->
        if(isGranted){
startCamera()
        }else{
            Toast.makeText(requireContext(),"Acepta los permisos",Toast.LENGTH_LONG).show()
        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
 if(checkCameraPermission()){
     startCamera()
 }else{
requestPermissionLauncher.launch(cameraPermison)
 }
    }

    private fun startCamera() {
        val camereProviderFuture=ProcessCameraProvider.getInstance(requireContext())
        camereProviderFuture.addListener(
            {
                val camereProvider :ProcessCameraProvider=camereProviderFuture.get()
                val preview =Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.viewFinder?.surfaceProvider)
                }

                val  cameraSelector=CameraSelector.DEFAULT_FRONT_CAMERA
                try {
                    camereProvider.unbindAll()
                    camereProvider.bindToLifecycle(this,cameraSelector,preview)
                }catch (e:Exception){
                    Log.e("Error","Algo esta mal ${e.message}")
                }
            },ContextCompat.getMainExecutor(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentPalmistryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun checkCameraPermission(): Boolean {
        return PermissionChecker.checkSelfPermission(
            requireContext(),
            cameraPermison
        ) == PermissionChecker.PERMISSION_GRANTED
    }


}