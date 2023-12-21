package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.skeep.mahjongvision.databinding.ActivitySurveyBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SurveyActivity : AppCompatActivity() {

    private val retrofit = RetrofitInstance.getInstance().create(API::class.java)
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

            //목업 체크박스 데이터
            val selectedFiveTong = binding.fiveTong.isChecked
            val selectedFiveMan = binding.fiveMan.isChecked
            val selectedFiveSac = binding.fiveSac.isChecked

            //예외 처리, RadioButton이 하나라도 선택 안 되어 있으면 진행하지 않음
            if (selectedWinningMethodId == -1 || selectedRichiId == -1 ||
                selectedPrevalentWindId == -1 || selectedSeatWindId == -1 || selectedSpecialPointsId == -1
            ){
                Toast.makeText(this, "모든 항목을 선택해주세요", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //정보 정리해서 서버로 던지기
                val stringData = intent.getStringExtra("message")
                //val stringData = "{\"win\":\"5s\",\"dora\":[\"6p\",\"7s\",\"1p\",\"8p\"],\"hand\":[\"2m\",\"3m\",\"4m\",\"7m\",\"8m\",\"9m\",\"5p\",\"5p\",\"3s\",\"4s\"],\"huro\":{\"chi\":[],\"pong\":[],\"ankkang\":[[\"8s\",\"8s\",\"8s\",\"8s\"]],\"minkkang\":[]}}"
                val surveyData = JSONObject()
                if(selectedWinningMethodId == binding.tsumo.id) {
                    surveyData.put("win_method", "tsumo")
                } else {
                    surveyData.put("win_method", "ron")
                }

                when(selectedRichiId) {
                    binding.noRichi.id -> {
                        surveyData.put("riichi", 0)
                    }
                    binding.oneRichi.id -> {
                        surveyData.put("riichi", 1)
                    }
                    else -> {
                        surveyData.put("riichi", 2)
                    }
                }

                if(binding.ippatsu.isChecked)
                    surveyData.put("ippatsu", true)
                else
                    surveyData.put("ippatsu", false)

                when (selectedSeatWindId) {
                    binding.seatWindEast.id -> {
                        surveyData.put("seat_wind", "E")
                    }
                    binding.seatWindWest.id -> {
                        surveyData.put("seat_wind", "W")
                    }
                    binding.seatWindSouth.id -> {
                        surveyData.put("seat_wind", "S")
                    }
                    else -> {
                        surveyData.put("seat_wind", "N")
                    }
                }

                when (selectedPrevalentWindId) {
                    binding.prevalentWindEast.id -> {
                        surveyData.put("prevalent_wind", "E")
                    }
                    binding.prevalentWindWest.id -> {
                        surveyData.put("prevalent_wind", "W")
                    }
                    binding.prevalentWindSouth.id -> {
                        surveyData.put("prevalent_wind", "S")
                    }
                    else -> {
                        surveyData.put("prevalent_wind", "N")
                    }
                }

                when (selectedSpecialPointsId) {
                    binding.changKkang.id -> {
                        surveyData.put("chankkang", true)
                        surveyData.put("haitei", false)
                        surveyData.put("rinshan", false)
                    }
                    binding.haiTei.id -> {
                        surveyData.put("chankkang", false)
                        surveyData.put("haitei", true)
                        surveyData.put("rinshan", false)
                    }
                    binding.youngSangGaeHwa.id -> {
                        surveyData.put("chankkang", false)
                        surveyData.put("haitei", false)
                        surveyData.put("rinshan", true)
                    }
                    else -> {
                        surveyData.put("chankkang", false)
                        surveyData.put("haitei", false)
                        surveyData.put("rinshan", false)
                    }
                }

                var akaNum = 0
                if(selectedFiveTong)
                    akaNum++
                if(selectedFiveMan)
                    akaNum++
                if(selectedFiveSac)
                    akaNum++

                surveyData.put("aka", akaNum)

                val jsonData = JSONObject()
                jsonData.putOpt("data", JSONObject(stringData))
                jsonData.putOpt("information", surveyData)


                Log.d("JsonData", jsonData.toString())
                retrofit.calculate(jsonData.toString()).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if(!response.isSuccessful) {
                            Toast.makeText(this@SurveyActivity, "통신 에러", Toast.LENGTH_SHORT).show()
                            Log.d("isSuccessful", response.toString())
                            return
                        }
                        Log.d("response data", response.body().toString())
                        val isChonbo = JSONObject(response.body()!!)
                        if(isChonbo.getJSONArray("yaku")[0] == "chonbo" ){
                            val intent = Intent(this@SurveyActivity, NoResultActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@SurveyActivity, ResultActivity::class.java)
                            intent.putExtra("message", response.body().toString())
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }

        }
    }


}