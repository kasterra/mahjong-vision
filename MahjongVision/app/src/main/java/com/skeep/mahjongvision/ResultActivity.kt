package com.skeep.mahjongvision

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.skeep.mahjongvision.databinding.ActivityResultBinding
import org.json.JSONObject



class ResultActivity : AppCompatActivity() {
    private val IS_MOCK = true
    private val MOCK_DATA =
        "{\"yaku\":[{\"name\":\"MenzenTsumo\",\"han\":1},{\"name\":\"Dora\",\"han\":4}],\"fu\":[\"base\",\"closed_kan\",\"tsumo\"],\"score\":[4000,2000]}"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if(IS_MOCK) MOCK_DATA else intent.getStringExtra("message")

        val jsonObject = JSONObject(data)
        val yakuArray = jsonObject.getJSONArray("yaku")
        val fuArray = jsonObject.getJSONArray("fu")
        val scoreArray = jsonObject.getJSONArray("score")
        
        var hans = 0
        var fus = 0

        for(i in 0 until yakuArray.length()){
            val yakuObj = yakuArray.getJSONObject(i)
            val objYakuName = yakuObj.getString("name")
            val yakuHan = yakuObj.getString("han")

            val (yakuName, yakuDescription) = mapData(this, objYakuName)

            val hanRow = createDetailRow(this, yakuName,yakuDescription,yakuHan)
            
            hans += yakuHan.toInt()
            
            binding.yakulist.addView(hanRow)
        }

        for(i in 0 until fuArray.length()){
            val fuItem = fuArray.getString(i)
            Log.d("111",fuItem)
            val (fuName, fuDescription, fuScore) = mapData(this, fuItem)

            val fuRow = createDetailRow(this, fuName, fuDescription, fuScore)
            
            fus += fuScore.toInt()
            
            binding.fulist.addView(fuRow)
        }
        
        fus = kotlin.math.round(fus/10.0).toInt() * 10
        
        binding.hanAndFu.text = "${hans}판 ${fus}부"
        
        binding.score.text = scoreArrToResult(JSONArrayToNative(scoreArray),
            JSONArrayContains(fuArray,"tsumo"))

        binding.goToHomeBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}