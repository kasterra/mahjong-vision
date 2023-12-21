package com.skeep.mahjongvision

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.android.volley.toolbox.Volley
import com.google.common.util.concurrent.ListenableFuture
import com.skeep.mahjongvision.databinding.ActivityCameraBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService


class CameraActivity : AppCompatActivity() {
    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var binding : ActivityCameraBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    private val retrofit = RetrofitInstance.getInstance().create(API::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))

        binding.pictureBtn.setOnClickListener{
            val intent = Intent(this,  ConfirmationActivity::class.java)
            Toast.makeText(this, "사진이 저장될 때까지 기다려 주세요.", Toast.LENGTH_SHORT).show()
            imageCapture?.takePicture(
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        Log.d("Image", image.toString())
                        val requestFile = RequestBody.create(MediaType.parse("image/png"), imageProxyToByteArray(image))
                        val body = MultipartBody.Part.createFormData("file", "asdf.png", requestFile)
                        retrofit.imageToData(body).enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (!response.isSuccessful) {
                                    Toast.makeText(this@CameraActivity, "사진을 다시 찍어주세요.", Toast.LENGTH_SHORT).show()
                                    return
                                }
                                val result = response.body()!!
                                val intent = Intent(this@CameraActivity, ConfirmationActivity::class.java)
                                intent.putExtra("message", result)
                                startActivity(intent)
                                Log.d("request", response.body()!!)
                            }
                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Log.e("request", "image_to_data fail")
                                Log.e("request", t.message!!)
                            }
                        })
                    }
                }
            )
        }
    }

    private fun imageProxyToByteArray(image: ImageProxy): ByteArray {
        val planeProxy = image.planes[0]
        val buffer: ByteBuffer = planeProxy.buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        return bytes
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