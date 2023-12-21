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
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.min

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

    lateinit var handPais:JSONArray
    lateinit var winPai:String
    lateinit var huroPais:MutableList<String>
    lateinit var doraPais:JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //api string data
        val data = intent.getStringExtra("message")
        Log.d("api data", data!!)
        val json = JSONTokener(data).nextValue() as JSONObject

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

        val doraJSON = json.getJSONArray("dora")
        val huro = json.getJSONObject("huro")
        val chi = huro.getJSONArray("chi")
        val pong = huro.getJSONArray("pong")
        val ankkang = huro.getJSONArray("ankkang")
        val minkkang = huro.getJSONArray("minkkang")


        //목업 데이터
        handPais = json.getJSONArray("hand")
        winPai = json.getString("win")
        huroPais = mutableListOf()
        doraPais = json.getJSONArray("dora")

        for (i in 0 until chi.length()) {
            for (j in 0 until chi.getJSONArray(i).length()) {
                huroPais.add(chi.getJSONArray(i)[j].toString())
            }
        }
        for (i in 0 until pong.length()) {
            for (j in 0 until pong.getJSONArray(i).length()) {
                huroPais.add(pong.getJSONArray(i)[j].toString())
            }
        }
        for (i in 0 until ankkang.length()) {
            for (j in 0 until ankkang.getJSONArray(i).length()) {
                huroPais.add(ankkang.getJSONArray(i)[j].toString())
            }
        }
        for (i in 0 until minkkang.length()) {
            for (j in 0 until minkkang.getJSONArray(i).length()) {
                huroPais.add(minkkang.getJSONArray(i)[j].toString())
            }
        }

        //손패 넣기
        for (i in 0 until handPais.length()) {
            val pai = handPais[i]
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

        //화료패 넣기
        binding.winPai.addView(ImageView(this).apply { 
            this.setImageResource(paiResourceMap[winPai]!!)
            this.adjustViewBounds = true
            this.maxHeight = 300
        })
        //paiResourceMap[winPai]?.let { ImageView(this).setImageResource(it) }
        //paiResourceMap[winPai]?.let { ImageView(this).setImageResource(it) }
        //ImageView(this).setImageResource(paiResourceMap[winPai]!!)

        //후로패 넣기
        for (pai in huroPais) {
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
            binding.huroPai.addView(paiview)
        }

        //도라패 넣기
        for (i in 0 until doraPais.length()) {
            val pai = doraPais[i]
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
            binding.doraPai.addView(paiview)
        }

        //맞아 틀려 버튼
        binding.acceptBtn.setOnClickListener {
            val intent = Intent(this, SurveyActivity::class.java)
            intent.putExtra("message", data)
            startActivity(intent)
        }

        binding.declineBtn.setOnClickListener {
            //val intent = Intent(this, CameraActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }
}