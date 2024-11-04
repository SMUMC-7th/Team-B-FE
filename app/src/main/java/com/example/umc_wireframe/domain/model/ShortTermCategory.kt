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

sealed interface ShortTermRegionObject {
    val region: String
    val x: Int
    val y: Int

    //서울
    sealed class Seoul(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object SeoulCity : Seoul("서울특별시", 60, 127)
        object Jongno : Seoul("종로구", 60, 127)
        object Jung : Seoul("중구", 60, 127)
        object Yongsan : Seoul("용산구", 60, 126)
        object Seongdong : Seoul("성동구", 61, 127)
        object Gwangjin : Seoul("광진구", 62, 126)
        object Dongdaemun : Seoul("동대문구", 61, 127)
        object Jungnang : Seoul("중랑구", 62, 128)
        object Seongbuk : Seoul("성북구", 61, 127)
        object Gangbuk : Seoul("강북구", 61, 128)
        object Dobong : Seoul("도봉구", 61, 129)
        object Nowon : Seoul("노원구", 61, 129)
        object Eunpyeong : Seoul("은평구", 59, 127)
        object Seodaemun : Seoul("서대문구", 59, 127)
        object Mapo : Seoul("마포구", 59, 127)
        object Yangcheon : Seoul("양천구", 58, 126)
        object Gangseo : Seoul("강서구", 58, 126)
        object Guro : Seoul("구로구", 58, 125)
        object Geumcheon : Seoul("금천구", 59, 124)
        object Yeongdeungpo : Seoul("영등포구", 58, 126)
        object Dongjak : Seoul("동작구", 59, 125)
        object Gwanak : Seoul("관악구", 59, 125)
        object Seocho : Seoul("서초구", 61, 125)
        object Gangnam : Seoul("강남구", 61, 126)
        object Songpa : Seoul("송파구", 62, 126)
        object Gangdong : Seoul("강동구", 62, 126)
    }

    //부산
    sealed class Busan(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object BusanCity : Busan("부산광역시", 98, 76)
        object Jung : Busan("중구", 97, 74)
        object Seo : Busan("서구", 97, 74)
        object Dong : Busan("동구", 98, 75)
        object Yeongdo : Busan("영도구", 98, 74)
        object Jin : Busan("부산진구", 97, 75)
        object Dongrae : Busan("동래구", 98, 76)
        object Nam : Busan("남구", 98, 75)
        object Buk : Busan("북구", 96, 76)
        object Haeundae : Busan("해운대구", 99, 75)
        object Saha : Busan("사하구", 96, 74)
        object Geumjeong : Busan("금정구", 98, 77)
        object Gangseo : Busan("강서구", 96, 76)
        object Yeonje : Busan("연제구", 98, 76)
        object Suyeong : Busan("수영구", 99, 75)
        object Sasang : Busan("사상구", 96, 75)
        object Gijang : Busan("기장군", 100, 77)
    }

    //대구
    sealed class Daegu(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object DaeguCity : Daegu("대구광역시", 89, 90)
        object Jung : Daegu("중구", 89, 90)
        object Dong : Daegu("동구", 90, 91)
        object Seo : Daegu("서구", 88, 90)
        object Nam : Daegu("남구", 89, 90)
        object Buk : Daegu("북구", 89, 91)
        object Suseong : Daegu("수성구", 89, 90)
        object Dalseo : Daegu("달서구", 88, 90)
        object Dalseong : Daegu("달성군", 86, 88)
        object Gunwi : Daegu("군위군", 88, 99)
    }

    //인천
    sealed class Incheon(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object IncheonCity : Incheon("인천광역시", 55, 124)
        object Jung : Incheon("중구", 54, 125)
        object Dong : Incheon("동구", 54, 125)
        object Michuhol : Incheon("미추홀구", 54, 124)
        object Yeonsu : Incheon("연수구", 55, 123)
        object Namdong : Incheon("남동구", 56, 124)
        object Bupyeong : Incheon("부평구", 55, 125)
        object Geyang : Incheon("계양구", 56, 126)
        object Seo : Incheon("서구", 55, 126)
        object Ganghwa : Incheon("강화군", 51, 130)
        object Ongjin : Incheon("옹진군", 54, 124)
    }

    //광주
    sealed class Gwangju(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object GwangjuCity : Gwangju("광주광역시", 58, 74)
        object Dong : Gwangju("동구", 60, 74)
        object Seo : Gwangju("서구", 59, 74)
        object Nam : Gwangju("남구", 59, 73)
        object Buk : Gwangju("북구", 59, 75)
        object Gwangsan : Gwangju("광산구", 57, 74)
    }

    //대전
    sealed class Daejeon(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object DaejeonCity : Daejeon("대전광역시", 67, 100)
        object Dong : Daejeon("동구", 68, 100)
        object Jung : Daejeon("중구", 68, 100)
        object Seo : Daejeon("서구", 67, 100)
        object Yuseong : Daejeon("유성구", 67, 101)
        object Daedeok : Daejeon("대덕구", 68, 100)
    }

    //울산
    sealed class Ulsan(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object UlsanCity : Ulsan("울산광역시", 102, 84)
        object Jung : Ulsan("중구", 102, 84)
        object Nam : Ulsan("남구", 102, 84)
        object Dong : Ulsan("동구", 104, 83)
        object Buk : Ulsan("북구", 103, 85)
        object Ulju : Ulsan("울주군", 101, 84)
    }

    //경기도
    sealed class Gyeonggi(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object GyeonggiProvince : Gyeonggi("경기도", 60, 120)
        object SuwonJanam : Gyeonggi("수원시장안구", 60, 121)
        object SuwonGwONSEON : Gyeonggi("수원시권선구", 60, 120)
        object SuwonPaldal : Gyeonggi("수원시팔달구", 61, 121)
        object SuwonYeongtong : Gyeonggi("수원시영통구", 61, 120)
        object SeongnamSuJeong : Gyeonggi("성남시수정구", 63, 124)
        object SeongnamJungwon : Gyeonggi("성남시중원구", 63, 124)
        object SeongnamBundang : Gyeonggi("성남시분당구", 62, 123)
        object Uijeongbu : Gyeonggi("의정부시", 61, 130)
        object AnyangManan : Gyeonggi("안양시만안구", 59, 123)
        object AnyangDongan : Gyeonggi("안양시동안구", 59, 123)
        object BucheonWonmi : Gyeonggi("부천시원미구", 57, 125)
        object BucheonSosa : Gyeonggi("부천시소사구", 57, 125)
        object BucheonOjeong : Gyeonggi("부천시오정구", 57, 126)
        object Gwangmyeong : Gyeonggi("광명시", 58, 125)
        object Pyeongtaek : Gyeonggi("평택시", 62, 114)
        object Dongducheon : Gyeonggi("동두천시", 61, 134)
        object AnsanSangrok : Gyeonggi("안산시상록구", 58, 121)
        object AnsanDanwon : Gyeonggi("안산시단원구", 57, 121)
        object GoyangDeokyang : Gyeonggi("고양시덕양구", 57, 128)
        object GoyangIlsanDong : Gyeonggi("고양시일산동구", 56, 129)
        object GoyangIlsanSeo : Gyeonggi("고양시일산서구", 56, 129)
        object Gwacheon : Gyeonggi("과천시", 60, 124)
        object Guri : Gyeonggi("구리시", 62, 127)
        object Namyangju : Gyeonggi("남양주시", 64, 128)
        object Osan : Gyeonggi("오산시", 62, 118)
        object Siheung : Gyeonggi("시흥시", 57, 123)
        object Gunpo : Gyeonggi("군포시", 59, 122)
        object Uiwang : Gyeonggi("의왕시", 60, 122)
        object Hanam : Gyeonggi("하남시", 64, 126)
        object YonginCheoin : Gyeonggi("용인시처인구", 64, 119)
        object YonginGiheung : Gyeonggi("용인시기흥구", 62, 120)
        object YonginSuji : Gyeonggi("용인시수지구", 62, 121)
        object Paju : Gyeonggi("파주시", 56, 131)
        object Ichun : Gyeonggi("이천시", 68, 121)
        object Anseong : Gyeonggi("안성시", 65, 115)
        object Kimpo : Gyeonggi("김포시", 55, 128)
        object Hwaseong : Gyeonggi("화성시", 57, 119)
        object Gwangju : Gyeonggi("광주시", 65, 123)
        object Yangju : Gyeonggi("양주시", 61, 131)
        object Pochun : Gyeonggi("포천시", 64, 134)
        object Yeoju : Gyeonggi("여주시", 71, 121)
        object Yeoncheon : Gyeonggi("연천군", 61, 138)
        object Gapyeong : Gyeonggi("가평군", 69, 133)
        object Yangpyeong : Gyeonggi("양평군", 69, 125)
    }

    //충청북도
    sealed class Chungcheongbukdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object ChungcheongbukProvince : Chungcheongbukdo("충청북도", 69, 107)
        object ChungjuSangdang : Chungcheongbukdo("청주시상당구", 69, 106)
        object ChungjuSeowon : Chungcheongbukdo("청주시서원구", 69, 107)
        object ChungjuHeungdeok : Chungcheongbukdo("청주시흥덕구", 67, 106)
        object ChungjuCheongwon : Chungcheongbukdo("청주시청원구", 69, 107)
        object Chungju : Chungcheongbukdo("충주시", 76, 114)
        object Jecheon : Chungcheongbukdo("제천시", 81, 118)
        object Boeun : Chungcheongbukdo("보은군", 73, 103)
        object Okcheon : Chungcheongbukdo("옥천군", 71, 99)
        object Yeongdong : Chungcheongbukdo("영동군", 74, 97)
        object Jeungpyeong : Chungcheongbukdo("증평군", 71, 110)
        object Jinchon : Chungcheongbukdo("진천군", 68, 111)
        object Goesan : Chungcheongbukdo("괴산군", 74, 111)
        object Eumseong : Chungcheongbukdo("음성군", 72, 113)
        object Danyang : Chungcheongbukdo("단양군", 84, 115)
    }

    //전라북도
    sealed class Chungcheongnamdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object ChungcheongnamProvince : Chungcheongnamdo("충청남도", 68, 100)
        object CheonanDongnam : Chungcheongnamdo("천안시동남구", 63, 110)
        object CheonanSeobuk : Chungcheongnamdo("천안시서북구", 63, 112)
        object Gongju : Chungcheongnamdo("공주시", 63, 102)
        object Boryeong : Chungcheongnamdo("보령시", 54, 100)
        object Asan : Chungcheongnamdo("아산시", 60, 110)
        object Seosan : Chungcheongnamdo("서산시", 51, 110)
        object Nonsan : Chungcheongnamdo("논산시", 62, 97)
        object Gyeryong : Chungcheongnamdo("계룡시", 65, 99)
        object Dangjin : Chungcheongnamdo("당진시", 54, 112)
        object Geumsan : Chungcheongnamdo("금산군", 69, 95)
        object Buyeo : Chungcheongnamdo("부여군", 59, 99)
        object Seocheon : Chungcheongnamdo("서천군", 55, 94)
        object Cheongyang : Chungcheongnamdo("청양군", 57, 103)
        object Hongseong : Chungcheongnamdo("홍성군", 55, 106)
        object Yesan : Chungcheongnamdo("예산군", 58, 107)
        object Taean : Chungcheongnamdo("태안군", 48, 109)
    }

    //전라남도
    sealed class Jeollanamdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object JeollanamProvince : Jeollanamdo("전라남도", 51, 67)
        object Mokpo : Jeollanamdo("목포시", 50, 67)
        object Yeosu : Jeollanamdo("여수시", 73, 66)
        object Suncheon : Jeollanamdo("순천시", 70, 70)
        object Naju : Jeollanamdo("나주시", 56, 71)
        object Gwangyang : Jeollanamdo("광양시", 73, 70)
        object Damyang : Jeollanamdo("담양군", 61, 78)
        object Gokseong : Jeollanamdo("곡성군", 66, 77)
        object Gurae : Jeollanamdo("구례군", 69, 75)
        object Goheung : Jeollanamdo("고흥군", 66, 62)
        object Boseong : Jeollanamdo("보성군", 62, 66)
        object Hwasun : Jeollanamdo("화순군", 61, 72)
        object Jangheung : Jeollanamdo("장흥군", 59, 64)
        object Gangjin : Jeollanamdo("강진군", 57, 63)
        object Haenam : Jeollanamdo("해남군", 54, 61)
        object Yeongam : Jeollanamdo("영암군", 56, 66)
        object Muan : Jeollanamdo("무안군", 52, 71)
        object Hampyeong : Jeollanamdo("함평군", 52, 72)
        object Yeonggwang : Jeollanamdo("영광군", 52, 77)
        object Jangseong : Jeollanamdo("장성군", 57, 77)
        object Wando : Jeollanamdo("완도군", 57, 56)
        object Jindo : Jeollanamdo("진도군", 48, 59)
        object Sinan : Jeollanamdo("신안군", 50, 66)
    }

    //경상북도
    sealed class Gyeongsangbukdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object GyeongsangbukProvince : Gyeongsangbukdo("경상북도", 87, 106)
        object PohangNam : Gyeongsangbukdo("포항시남구", 102, 94)
        object PohangBuk : Gyeongsangbukdo("포항시북구", 102, 95)
        object Gyeongju : Gyeongsangbukdo("경주시", 100, 91)
        object Kimcheon : Gyeongsangbukdo("김천시", 80, 96)
        object Andong : Gyeongsangbukdo("안동시", 91, 106)
        object Gumi : Gyeongsangbukdo("구미시", 84, 96)
        object Yeongju : Gyeongsangbukdo("영주시", 89, 111)
        object Yeongcheon : Gyeongsangbukdo("영천시", 95, 93)
        object Sangju : Gyeongsangbukdo("상주시", 81, 102)
        object Mungyeong : Gyeongsangbukdo("문경시", 81, 106)
        object Gyeongsan : Gyeongsangbukdo("경산시", 91, 90)
        object Uiseong : Gyeongsangbukdo("의성군", 90, 101)
        object Cheongsong : Gyeongsangbukdo("청송군", 96, 103)
        object Yeongyang : Gyeongsangbukdo("영양군", 97, 108)
        object Yeongdeok : Gyeongsangbukdo("영덕군", 102, 103)
        object Cheongdo : Gyeongsangbukdo("청도군", 91, 86)
        object Goryeong : Gyeongsangbukdo("고령군", 83, 87)
        object Seongju : Gyeongsangbukdo("성주군", 83, 91)
        object Chilgok : Gyeongsangbukdo("칠곡군", 85, 93)
        object Yecheon : Gyeongsangbukdo("예천군", 86, 107)
        object Bonghwa : Gyeongsangbukdo("봉화군", 90, 113)
        object Uljin : Gyeongsangbukdo("울진군", 102, 115)
        object Ulleung : Gyeongsangbukdo("울릉군", 127, 127)
    }

    //경상남도
    sealed class Gyeongsangnamdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object GyeongsangnamProvince : Gyeongsangnamdo("경상남도", 91, 77)
        object ChangwonUicha : Gyeongsangnamdo("창원시의창구", 90, 77)
        object ChangwonSeongsan : Gyeongsangnamdo("창원시성산구", 91, 76)
        object ChangwonMasanHappo : Gyeongsangnamdo("창원시마산합포구", 89, 76)
        object ChangwonMasanHwian : Gyeongsangnamdo("창원시마산회원구", 89, 76)
        object ChangwonJinhae : Gyeongsangnamdo("창원시진해구", 91, 75)
        object Jinju : Gyeongsangnamdo("진주시", 81, 75)
        object Tongyeong : Gyeongsangnamdo("통영시", 87, 68)
        object Sachaen : Gyeongsangnamdo("사천시", 80, 71)
        object Gimhae : Gyeongsangnamdo("김해시", 95, 77)
        object Milyang : Gyeongsangnamdo("밀양시", 92, 83)
        object Geoje : Gyeongsangnamdo("거제시", 90, 69)
        object Yangsan : Gyeongsangnamdo("양산시", 97, 79)
        object Uiryeong : Gyeongsangnamdo("의령군", 83, 78)
        object Haman : Gyeongsangnamdo("함안군", 86, 77)
        object Changnyeong : Gyeongsangnamdo("창녕군", 87, 83)
        object Goeseong : Gyeongsangnamdo("고성군", 85, 71)
        object Namhae : Gyeongsangnamdo("남해군", 77, 68)
        object Hadong : Gyeongsangnamdo("하동군", 74, 73)
        object Sancheong : Gyeongsangnamdo("산청군", 76, 80)
        object Hamyang : Gyeongsangnamdo("함양군", 74, 82)
        object Geochang : Gyeongsangnamdo("거창군", 77, 86)
        object Hapcheon : Gyeongsangnamdo("합천군", 81, 84)
    }

    // 제주특별자치도 지역
    sealed class Jeju(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object JejuCity : Jeju("제주특별자치도", 52, 38)
        object JejuJeju : Jeju("제주시", 53, 38)
        object Seogwipo : Jeju("서귀포시", 52, 33)
    }

    // 강원특별자치도 지역
    sealed class Gangwon(override val region: String, override val x: Int, override val y: Int) :
        ShortTermRegionObject {
        object GangwonProvince : Gangwon("강원특별자치도", 73, 134)
        object Chuncheon : Gangwon("춘천시", 73, 134)
        object Wonju : Gangwon("원주시", 76, 122)
        object Gangneung : Gangwon("강릉시", 92, 131)
        object Donghae : Gangwon("동해시", 97, 127)
        object Taebek : Gangwon("태백시", 95, 119)
        object Sokcho : Gangwon("속초시", 87, 141)
        object Samcheok : Gangwon("삼척시", 98, 125)
        object Hongcheon : Gangwon("홍천군", 75, 130)
        object Hwangseong : Gangwon("횡성군", 77, 125)
        object Yeongwol : Gangwon("영월군", 86, 119)
        object Pyeongchang : Gangwon("평창군", 84, 123)
        object Jeongseon : Gangwon("정선군", 89, 123)
        object Cheorwon : Gangwon("철원군", 65, 139)
        object Hwacheon : Gangwon("화천군", 72, 139)
        object Yanggu : Gangwon("양구군", 77, 139)
        object Inje : Gangwon("인제군", 80, 138)
        object GoseongGW : Gangwon("고성군", 85, 145)
        object Yangyang : Gangwon("양양군", 88, 138)
    }

    // 전라북도 지역
    sealed class Jeollabukdo(
        override val region: String,
        override val x: Int,
        override val y: Int
    ) : ShortTermRegionObject {
        object JeollabukProvince : Jeollabukdo("전북특별자치도", 63, 89)
        object JeonjuWansan : Jeollabukdo("전주시완산구", 63, 89)
        object JeonjuDeokjin : Jeollabukdo("전주시덕진구", 63, 89)
        object Gunsan : Jeollabukdo("군산시", 56, 92)
        object Iksan : Jeollabukdo("익산시", 60, 91)
        object Jeongeup : Jeollabukdo("정읍시", 58, 83)
        object Namwon : Jeollabukdo("남원시", 68, 80)
        object Kimje : Jeollabukdo("김제시", 59, 88)
        object Wanju : Jeollabukdo("완주군", 63, 89)
        object Jinan : Jeollabukdo("진안군", 68, 88)
        object Muju : Jeollabukdo("무주군", 72, 93)
        object Jangsu : Jeollabukdo("장수군", 70, 85)
        object Imsil : Jeollabukdo("임실군", 66, 84)
        object Sunchang : Jeollabukdo("순창군", 63, 79)
        object Gochang : Jeollabukdo("고창군", 56, 80)
        object Buan : Jeollabukdo("부안군", 56, 87)
    }

    data object IeodoIsland : ShortTermRegionObject {
        override val region: String = "이어도"
        override val x: Int = 28
        override val y: Int = 8
    }

    data object SejongCity : ShortTermRegionObject {
        override val region: String = "세종특별자치시"
        override val x: Int = 66
        override val y: Int = 103
    }

    data class Temp(
        override val region: String,
        override val x: Int,
        override val y: Int,
    ) : ShortTermRegionObject
}

fun getSeoulRegions(): List<ShortTermRegionObject.Seoul> {
    return ShortTermRegionObject.Seoul::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getBusanRegions(): List<ShortTermRegionObject.Busan> {
    return ShortTermRegionObject.Busan::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getDaeguRegions(): List<ShortTermRegionObject.Daegu> {
    return ShortTermRegionObject.Daegu::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getDaejeonRegions(): List<ShortTermRegionObject.Daejeon> {
    return ShortTermRegionObject.Daejeon::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getChungcheongbukdoRegions(): List<ShortTermRegionObject.Chungcheongbukdo> {
    return ShortTermRegionObject.Chungcheongbukdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getChungcheongnamdoRegions(): List<ShortTermRegionObject.Chungcheongnamdo> {
    return ShortTermRegionObject.Chungcheongnamdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getGangwonRegions(): List<ShortTermRegionObject.Gangwon> {
    return ShortTermRegionObject.Gangwon::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getGyeonggiRegions(): List<ShortTermRegionObject.Gyeonggi> {
    return ShortTermRegionObject.Gyeonggi::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getJejuRegions(): List<ShortTermRegionObject.Jeju> {
    return ShortTermRegionObject.Jeju::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getJeollabukdoRegions(): List<ShortTermRegionObject.Jeollabukdo> {
    return ShortTermRegionObject.Jeollabukdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getJeollanamdoRegions(): List<ShortTermRegionObject.Jeollanamdo> {
    return ShortTermRegionObject.Jeollanamdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getUlsanRegions(): List<ShortTermRegionObject.Ulsan> {
    return ShortTermRegionObject.Ulsan::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getIncheonRegions(): List<ShortTermRegionObject.Incheon> {
    return ShortTermRegionObject.Incheon::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getGwangjuRegions(): List<ShortTermRegionObject.Gwangju> {
    return ShortTermRegionObject.Gwangju::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getGyeongsangbukdoRegions(): List<ShortTermRegionObject.Gyeongsangbukdo> {
    return ShortTermRegionObject.Gyeongsangbukdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun getGyeongsangnamdoRegions(): List<ShortTermRegionObject.Gyeongsangnamdo> {
    return ShortTermRegionObject.Gyeongsangnamdo::class.sealedSubclasses.mapNotNull { it.objectInstance }
}

fun List<String>.toShorTermRegion() = this.map { ShortTermRegionObject.Temp(it, 0, 0) }
