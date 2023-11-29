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

            //목업 라디오 버튼 데이터
            val selectedWinningMethodId = binding.winningMethod.checkedRadioButtonId
            val selectedRichiId = binding.isRichi.checkedRadioButtonId
            val selectedPrevalentWindId = binding.prevalentWind.checkedRadioButtonId
            val selectedSeatWindId = binding.seatWind.checkedRadioButtonId
            val selectedSpecialPointsId = binding.specialPoints.checkedRadioButtonId

            //예외 처리, RadioButton이 하나라도 선택 안 되어 있으면 진행하지 않음
            if (selectedWinningMethodId == -1 || selectedRichiId == -1 ||
                selectedPrevalentWindId == -1 || selectedSeatWindId == -1 || selectedSpecialPointsId == -1
            ){
                Toast.makeText(this, "모든 항목을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //정보 정리해서 서버로 던지기


                //화면 전환
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            }

        }
    }


}