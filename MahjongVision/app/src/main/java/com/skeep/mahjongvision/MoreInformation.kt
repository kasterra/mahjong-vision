package com.skeep.mahjongvision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.skeep.mahjongvision.databinding.ActivityMoreInformationBinding

class MoreInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMoreInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postBtn.setOnClickListener{
            Toast.makeText(this,"아직 미구현", Toast.LENGTH_SHORT).show();
        }
    }
}