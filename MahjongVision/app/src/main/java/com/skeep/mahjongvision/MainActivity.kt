package com.skeep.mahjongvision

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.skeep.mahjongvision.databinding.ActivityMainBinding

/*
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import android.content.res.Resources
import android.graphics.BitmapFactory
*/

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    //카메라 퍼미션 요청
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionMark.setOnClickListener {
            //Log.d("question Mark", "clicked")
            val intent = Intent(this, GuideActivity::class.java)
            startActivity(intent)
        }

        binding.startBtn.setOnClickListener {
            checkCameraPermissionAndStart()
        }

        /*
        val resource : Resources = this.resources
        binding.mainImage.setImageBitmap(BitmapFactory.decodeResource(resource, R.drawable.majan_man))
        */ //drawable -> bitmap

            /*=
        val getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
                bitmap ->
            bitmap.let { }
        }

        binding.startBtn.setOnClickListener {
            try {
                getTakePicture.launch(null)
            } catch (e :Exception){
                Toast.makeText(this,"something is wrong...", Toast.LENGTH_SHORT)
            }
        }
*/
    }

    private fun startCameraActivity()
    {

    }

    //카메라 퍼미션 허가 되어있는지 확인 후 실행
    private fun checkCameraPermissionAndStart() {
        val intent = Intent(this, CameraActivity::class.java)
        val cameraPermission = android.Manifest.permission.CAMERA
        val permissionStatus = ContextCompat.checkSelfPermission(
            this,
            cameraPermission)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            // 권한이 부여된 경우 카메라 액티비티 실행
            startActivity(intent)
        } else {
            // 권한 요청
            requestPermissionLauncher.launch(cameraPermission)
        }
    }
}