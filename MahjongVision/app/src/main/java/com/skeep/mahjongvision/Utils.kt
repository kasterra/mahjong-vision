package com.skeep.mahjongvision
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.view.ViewGroup.LayoutParams

fun mapData(context:Context, yakuName:String): Array<String> {
    val arr =  when (yakuName){
        "Riichi" -> context.resources.getStringArray(R.array.Riichi)
        "MenzenTsumo" -> context.resources.getStringArray(R.array.MenzenTsumo)
        "Ippatsu" -> context.resources.getStringArray(R.array.Ippatsu)
        "Rinshan" -> context.resources.getStringArray(R.array.Rinshan)
        "Chankan" -> context.resources.getStringArray(R.array.Chankan)
        "Haitei" -> context.resources.getStringArray(R.array.Haitei)
        "Houtei" -> context.resources.getStringArray(R.array.Houtei)
        "Pinfu" -> context.resources.getStringArray(R.array.Pinfu)
        "Iipeiko" -> context.resources.getStringArray(R.array.Iipeiko)
        "Yakuhai(Haku)" -> context.resources.getStringArray(R.array.Haku)
        "Yakuhai(Hatsu)" -> context.resources.getStringArray(R.array.Hatsu)
        "Yakuhai(Chun)" -> context.resources.getStringArray(R.array.Chun)
        "Yakuhai(windofplace)"->context.resources.getStringArray(R.array.windofplace)
        "Yakuhai(windofround)"->context.resources.getStringArray(R.array.windofround)
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
        "Dora"->context.resources.getStringArray(R.array.Dora)
        "Akadora"->context.resources.getStringArray(R.array.Akadora)

        "base"->context.resources.getStringArray(R.array.base)
        "penchan"->context.resources.getStringArray(R.array.penchan)
        "kanchan"->context.resources.getStringArray(R.array.kanchan)
        "valued_pair"->context.resources.getStringArray(R.array.valued_pair)
        "double_valued_pair"->context.resources.getStringArray(R.array.double_valued_pair)
        "pair_wait"->context.resources.getStringArray(R.array.pair_wait)
        "tsumo"->context.resources.getStringArray(R.array.tsumo)
        "hand_without_fu"->context.resources.getStringArray(R.array.hand_without_fu)
        "closed_pon"->context.resources.getStringArray(R.array.closed_pon)
        "open_pon"->context.resources.getStringArray(R.array.open_pon)
        "closed_terminal_pon"->context.resources.getStringArray(R.array.closed_terminal_pon)
        "open_terminal_pon"->context.resources.getStringArray(R.array.open_terminal_pon)
        "closed_kan"->context.resources.getStringArray(R.array.closed_kan)
        "closed_terminal_kan"->context.resources.getStringArray(R.array.closed_terminal_kan)
        "open_kan"->context.resources.getStringArray(R.array.open_kan)
        "open_terminal_kan"->context.resources.getStringArray(R.array.open_terminal_kan)
        else -> arrayOf("error", "what is the error")
    }
    return arr
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
