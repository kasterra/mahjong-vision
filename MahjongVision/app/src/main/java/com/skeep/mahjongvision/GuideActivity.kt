package com.skeep.mahjongvision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skeep.mahjongvision.databinding.ActivityGuideBinding

class GuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener{
            finish()
        }
    }
}