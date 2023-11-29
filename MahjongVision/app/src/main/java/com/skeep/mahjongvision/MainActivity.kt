package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
        /*
        val resource : Resources = this.resources
        binding.mainImage.setImageBitmap(BitmapFactory.decodeResource(resource, R.drawable.majan_man))
        */ //drawable -> bitmap

/*
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
}