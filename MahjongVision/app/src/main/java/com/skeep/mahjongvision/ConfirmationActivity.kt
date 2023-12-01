package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.skeep.mahjongvision.databinding.ActivityConfirmationBinding
import com.skeep.mahjongvision.databinding.ActivityGuideBinding

class ConfirmationActivity : AppCompatActivity() {

    //모든 패 맵
    val paiResourceMap: Map<String, Int> = mapOf(
        "1m" to R.drawable.majan1_manzu1,
        "2m" to R.drawable.majan1_manzu2,
        "3m" to R.drawable.majan1_manzu3,
        "4m" to R.drawable.majan1_manzu4,
        "5m" to R.drawable.majan1_manzu5,
        "6m" to R.drawable.majan1_manzu6,
        "7m" to R.drawable.majan1_manzu7,
        "8m" to R.drawable.majan1_manzu8,
        "9m" to R.drawable.majan1_manzu9,
        "1p" to R.drawable.majan2_pinzu1,
        "2p" to R.drawable.majan2_pinzu2,
        "3p" to R.drawable.majan2_pinzu3,
        "4p" to R.drawable.majan2_pinzu4,
        "5p" to R.drawable.majan2_pinzu5,
        "6p" to R.drawable.majan2_pinzu6,
        "7p" to R.drawable.majan2_pinzu7,
        "8p" to R.drawable.majan2_pinzu8,
        "9p" to R.drawable.majan2_pinzu9,
        "1s" to R.drawable.majan3_souzu1,
        "2s" to R.drawable.majan3_souzu2,
        "3s" to R.drawable.majan3_souzu3,
        "4s" to R.drawable.majan3_souzu4,
        "5s" to R.drawable.majan3_souzu5,
        "6s" to R.drawable.majan3_souzu6,
        "7s" to R.drawable.majan3_souzu7,
        "8s" to R.drawable.majan3_souzu8,
        "9s" to R.drawable.majan3_souzu9,
        "1z" to R.drawable.majan4_sufonpai_east,
        "2z" to R.drawable.majan4_sufonpai_south,
        "3z" to R.drawable.majan4_sufonpai_west,
        "4z" to R.drawable.majan4_sufonpai_north,
        "5z" to R.drawable.majan5_sangenpai1,
        "6z" to R.drawable.majan5_sangenpai2,
        "7z" to R.drawable.majan5_sangenpai3
    )

    lateinit var handPais:List<String>
    lateinit var winPais:String
    lateinit var huroPais:List<String>
    lateinit var doraPais:List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "https://jsonplaceholder.typicode.com/users" // 주소

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                Log.d("get", it)
                Toast.makeText(this, "get", Toast.LENGTH_SHORT).show()
                //val intent= Intent(this, SurveyActivity::class.java)
                //startActivity(intent)
            },
            { error->
                Log.d("get", "error $error")
            }
        )
        //GET

        //queue.add(stringRequest)
        binding.testbtn.setOnClickListener{
            queue.add(stringRequest)
        }

        //목업 데이터
        handPais = listOf("1m", "2m", "3m", "4m", "5m", "6m", "7m", "8m", "9m")

        //패 넣는 부분 목업 코드
        for (pai in handPais) {
            val paiview = ImageView(this)
            val resource = paiResourceMap[pai]

            paiview.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            if (resource != null) {
                paiview.setImageResource(resource)
            }
            // 이미지뷰를 컨테이너에 추가
            binding.handPai.addView(paiview)
        }

        //맞아 틀려 버튼
        binding.acceptBtn.setOnClickListener {
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
        }

        binding.declineBtn.setOnClickListener {
            //val intent = Intent(this, CameraActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}