package com.skeep.mahjongvision
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.view.ViewGroup.LayoutParams

fun mapData(context:Context, yakuName:String) {
    val arr =  when (yakuName){
        "Riichi" -> context.resources.getStringArray(R.array.Riichi)
        "Tsumo" -> context.resources.getStringArray(R.array.Tsumo)
        "Ippatsu" -> context.resources.getStringArray(R.array.Ippatsu)
        "Rinshan" -> context.resources.getStringArray(R.array.Rinshan)
        "Chankan" -> context.resources.getStringArray(R.array.Chankan)
        "Haitei" -> context.resources.getStringArray(R.array.Haitei)
        "Houtei" -> context.resources.getStringArray(R.array.Houtei)
        "Pinfu" -> context.resources.getStringArray(R.array.Pinfu)
        "Iipeiko" -> context.resources.getStringArray(R.array.Iipeiko)
        "Haku" -> context.resources.getStringArray(R.array.Haku)
        "Hatsu" -> context.resources.getStringArray(R.array.Hatsu)
        "Chun" -> context.resources.getStringArray(R.array.Chun)
        "Tanyao" -> context.resources.getStringArray(R.array.Tanyao)
        "DaburuRiichi"->context.resources.getStringArray(R.array.DaburuRiichi)
        "Chiitoitsu" -> context.resources.getStringArray(R.array.Chiitoitsu)
        "Sanshoku" -> context.resources.getStringArray(R.array.Sanshoku)
        "Ittsu" -> context.resources.getStringArray(R.array.Ittsu)
        "Toitoi" -> context.resources.getStringArray(R.array.Toitoi)
        "Sanankou" -> context.resources.getStringArray(R.array.Sanankou)
        "SanshokuDoukou"->context.resources.getStringArray(R.array.SanshokuDoukou)
        "SanKantsu"->context.resources.getStringArray(R.array.SanKantsu)
        "Chantai"->context.resources.getStringArray(R.array.Chantai)
        "Honroto"->context.resources.getStringArray(R.array.Honroto)
        "Shosangen"->context.resources.getStringArray(R.array.Shosangen)
        "Ryanpeikou"->context.resources.getStringArray(R.array.Ryanpeikou)
        "Junchan"->context.resources.getStringArray(R.array.Junchan)
        "Honitsu"->context.resources.getStringArray(R.array.Honitsu)
        "Chinitsu"->context.resources.getStringArray(R.array.Chinitsu)
        "Suuankou"->context.resources.getStringArray(R.array.Suuankou)
        "SuuankouTanki"->context.resources.getStringArray(R.array.SuuankouTanki)
        "KokushiMusou"->context.resources.getStringArray(R.array.KokushiMusou)
        "DaburuKokushiMusou"->context.resources.getStringArray(R.array.DaburuKokushiMusou)
        "ChuurenPoutou"->context.resources.getStringArray(R.array.ChuurenPoutou)
        "DaburuChuurenPoutou"->context.resources.getStringArray(R.array.DaburuChuurenPoutou)
        "Ryuuiisou"->context.resources.getStringArray(R.array.Ryuuiisou)
        "Tsuuiisou"->context.resources.getStringArray(R.array.Tsuuiisou)
        "Chinroutou"->context.resources.getStringArray(R.array.Chinroutou)
        "Daisangen"->context.resources.getStringArray(R.array.Daisangen)
        "Shousuushii"->context.resources.getStringArray(R.array.Shousuushii)
        "Daisuushii"->context.resources.getStringArray(R.array.Daisuushii)
        "Suukantsu"->context.resources.getStringArray(R.array.Suukantsu)
        else -> {
            when {
                yakuName.matches("Dora\\d+".toRegex()) -> context.resources.getStringArray(R.array.Dora)
                yakuName.matches("Akadora\\d+".toRegex()) -> context.resources.getStringArray(R.array.Akadora)
                else -> arrayOf("error", "what is the error")
            }
        }
    }
}



fun createDetailRow(
    context: Context,
    titleText: String,
    descriptionText: String,
    scoreText: String
): LinearLayout {
    // 최상위 LinearLayout 생성
    val mainLayout = LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.HORIZONTAL
        weightSum = 3f
        setPadding(10, 10, 10, 10)
    }

    // 좌측 Vertical LinearLayout 생성
    val leftLayout = LinearLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 2f)
        orientation = LinearLayout.VERTICAL
    }

    // 제목 TextView 생성
    val titleTextView = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        text = titleText
        textSize = 25f
    }

    // 설명 TextView 생성
    val descriptionTextView = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        text = descriptionText
        textSize = 20f
        setTextColor(Color.parseColor("#AAAAAA"))
    }

    // 점수 TextView 생성
    val scoreTextView = TextView(context).apply {
        layoutParams = LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        gravity = Gravity.CENTER
        text = scoreText
        textSize = 25f
    }

    leftLayout.addView(titleTextView)
    leftLayout.addView(descriptionTextView)

    mainLayout.addView(leftLayout)
    mainLayout.addView(scoreTextView)

    return mainLayout
}
