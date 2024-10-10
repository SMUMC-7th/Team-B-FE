package com.example.umc_wireframe.domain.model

enum class ShortTermCategory {
    POP, // 강수확률
    PTY, // 강수형태
    PCP, // 1시간 강수량 mm
    REH, // 습도
    SNO, // 시간 신적설 cm
    SKY, // 하늘상태 맑음(1), 구름많음(3), 흐림(4)
    TMP, // 1시간 기온
    TMN, // 일 최저 기온
    TMX, // 일 최고 기온
    UUU, // 풍속(동서) m/s
    VVV, // 풍속(남북) m/s
    WAV, // 파고 M
    VEC, // 풍향 deg
    WSD, // 풍속 m/s
    ERROR // 에러
}

fun ShortTermCategory.toString(): String = when (this) {
    ShortTermCategory.POP -> "POP"  // 강수확률
    ShortTermCategory.PTY -> "PTY"  // 강수형태
    ShortTermCategory.PCP -> "PCP"  // 1시간 강수량 mm
    ShortTermCategory.REH -> "REH"  // 습도
    ShortTermCategory.SNO -> "SNO"  // 시간 신적설 cm
    ShortTermCategory.SKY -> "SKY"  // 하늘상태
    ShortTermCategory.TMP -> "TMP"  // 1시간 기온
    ShortTermCategory.TMN -> "TMN"  // 일 최저 기온
    ShortTermCategory.TMX -> "TMX"  // 일 최고 기온
    ShortTermCategory.UUU -> "UUU"  // 풍속(동서)
    ShortTermCategory.VVV -> "VVV"  // 풍속(남북)
    ShortTermCategory.WAV -> "WAV"  // 파고
    ShortTermCategory.VEC -> "VEC"  // 풍향
    ShortTermCategory.WSD -> "WSD"  // 풍속
    ShortTermCategory.ERROR -> "ERROR"
}

fun String.toShortTermCategory(): ShortTermCategory = when (this) {
    "POP" -> ShortTermCategory.POP  // 강수확률
    "PTY" -> ShortTermCategory.PTY  // 강수형태
    "PCP" -> ShortTermCategory.PCP  // 1시간 강수량 mm
    "REH" -> ShortTermCategory.REH  // 습도
    "SNO" -> ShortTermCategory.SNO  // 시간 신적설 cm
    "SKY" -> ShortTermCategory.SKY  // 하늘상태
    "TMP" -> ShortTermCategory.TMP  // 1시간 기온
    "TMN" -> ShortTermCategory.TMN  // 일 최저 기온
    "TMX" -> ShortTermCategory.TMX  // 일 최고 기온
    "UUU" -> ShortTermCategory.UUU  // 풍속(동서)
    "VVV" -> ShortTermCategory.VVV  // 풍속(남북)
    "WAV" -> ShortTermCategory.WAV  // 파고
    "VEC" -> ShortTermCategory.VEC  // 풍향
    "WSD" -> ShortTermCategory.WSD  // 풍속
    else -> ShortTermCategory.ERROR  // 에러
}










