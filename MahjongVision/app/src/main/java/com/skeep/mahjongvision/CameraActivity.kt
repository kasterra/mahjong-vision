package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.google.common.util.concurrent.ListenableFuture
import com.skeep.mahjongvision.databinding.ActivityCameraBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var binding : ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)
        val url = "http://175.45.194.234:8000/image_to_data"


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        //cameraExecutor = Executors.newSingleThreadExecutor()

        val volleyMultipartRequest = VolleyMultipartRequest(
            Request.Method.POST,
            url,
            {
                val intent = Intent(this,  ConfirmationActivity::class.java)
                Log.d("dogdriip", "success")

                Toast.makeText(this@CameraActivity, "성공!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            },{
                val intent = Intent(this,  ConfirmationActivity::class.java)
                Log.d("dogdriip", "failed")
                Toast.makeText(this@CameraActivity, "실패...", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            });

        binding.pictureBtn.setOnClickListener{
            Toast.makeText(this, "사진이 저장될 때까지 기다려 주세요.", Toast.LENGTH_SHORT).show()
            imageCapture?.takePicture(ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        Log.d("Image", image.toString())

                        queue.add(volleyMultipartRequest)

                    }
                })
        }
    }

    private fun bindPreview(cameraProvider : ProcessCameraProvider) {
        var preview : Preview = Preview.Builder()
            .build()

        var cameraSelector : CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        imageCapture = ImageCapture.Builder().setTargetRotation(binding.root.display.rotation).build()

        var camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageCapture)

        preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
    }
}