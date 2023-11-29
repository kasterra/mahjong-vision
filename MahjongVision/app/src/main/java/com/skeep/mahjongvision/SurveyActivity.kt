package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.skeep.mahjongvision.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.postBtn.setOnClickListener{
            /*
            Toast.makeText(this,"아직 미구현", Toast.LENGTH_SHORT).show();
             */
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }
    }
}