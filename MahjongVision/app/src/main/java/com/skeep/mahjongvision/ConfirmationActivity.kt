package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skeep.mahjongvision.databinding.ActivityConfirmationBinding
import com.skeep.mahjongvision.databinding.ActivityGuideBinding

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.acceptBtn.setOnClickListener {
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
        }

        binding.declineBtn.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}