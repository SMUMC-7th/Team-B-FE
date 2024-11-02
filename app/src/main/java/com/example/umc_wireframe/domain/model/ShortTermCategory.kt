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

enum class ShortTermRegion(val region: String, val x: Int, val y: Int) {
    //서울
    JONGNO("종로구", 60, 127),
    JUNG("중구", 60, 127),
    YONGSAN("용산구", 60, 126),
    SEONGDONG("성동구", 61, 127),
    GWANGJIN("광진구", 62, 126),
    DONGDAEMUN("동대문구", 61, 127),
    JUNGGANG("중랑구", 62, 128),
    SEONGBUK("성북구", 61, 127),
    GANGBUK("강북구", 61, 128),
    DOBONG("도봉구", 61, 129),
    NOWON("노원구", 61, 129),
    EUNPYEONG("은평구", 59, 127),
    SEODAEMUN("서대문구", 59, 127),
    MAPO("마포구", 59, 127),
    YANGCHEON("양천구", 58, 126),
    GANGSEO("강서구", 58, 126),
    GURO("구로구", 58, 125),
    GEUMCHEON("금천구", 59, 124),
    YEONGDEUNGPO("영등포구", 58, 126),
    DONGJAK("동작구", 59, 125),
    GWANAK("관악구", 59, 125),
    SEOCHO("서초구", 61, 125),
    GANGNAM("강남구", 61, 126),
    SONGPA("송파구", 62, 126),
    GANGDONG("강동구", 62, 126),

    // 부산
    BUSAN_JUNG("중구", 97, 74),
    BUSAN_SEO("서구", 97, 74),
    BUSAN_DONG("동구", 98, 75),
    BUSAN_YEONGDO("영도구", 98, 74),
    BUSAN_JIN("부산진구", 97, 75),
    BUSAN_DONGRAE("동래구", 98, 76),
    BUSAN_NAM("남구", 98, 75),
    BUSAN_BUK("북구", 96, 76),
    BUSAN_HAEUNDAE("해운대구", 99, 75),
    BUSAN_SAHA("사하구", 96, 74),
    BUSAN_GEUMJEONG("금정구", 98, 77),
    BUSAN_GANGSEO("강서구", 96, 76),
    BUSAN_YEONJE("연제구", 98, 76),
    BUSAN_SUYEONG("수영구", 99, 75),
    BUSAN_SASANG("사상구", 96, 75),
    BUSAN_GIJANG("기장군", 100, 77),

    // 대구 지역
    DAEGU_JUNG("중구", 89, 90),
    DAEGU_DONG("동구", 90, 91),
    DAEGU_SEO("서구", 88, 90),
    DAEGU_NAM("남구", 89, 90),
    DAEGU_BUK("북구", 89, 91),
    DAEGU_SUSEONG("수성구", 89, 90),
    DAEGU_DALSEO("달서구", 88, 90),
    DAEGU_DALSEONG("달성군", 86, 88),
    DAEGU_GUNWI("군위군", 88, 99),

    // 인천 지역
    INCHEON_JUNG("중구", 54, 125),
    INCHEON_DONG("동구", 54, 125),
    INCHEON_MICHUHOL("미추홀구", 54, 124),
    INCHEON_YEONSU("연수구", 55, 123),
    INCHEON_NAMDONG("남동구", 56, 124),
    INCHEON_BUPYEONG("부평구", 55, 125),
    INCHEON_GEYANG("계양구", 56, 126),
    INCHEON_SEO("서구", 55, 126),
    INCHEON_GANGHWA("강화군", 51, 130),
    INCHEON_ONGJIN("옹진군", 54, 124),

    // 광주 지역
    GWANGJU_DONG("동구", 60, 74),
    GWANGJU_SEO("서구", 59, 74),
    GWANGJU_NAM("남구", 59, 73),
    GWANGJU_BUK("북구", 59, 75),
    GWANGJU_GWANGSAN("광산구", 57, 74),

    // 대전 지역
    DAEJEON_DONG("동구", 68, 100),
    DAEJEON_JUNG("중구", 68, 100),
    DAEJEON_SEO("서구", 67, 100),
    DAEJEON_YUSEONG("유성구", 67, 101),
    DAEJEON_DAEDEOK("대덕구", 68, 100),

    // 울산 지역
    ULSAN_JUNG("중구", 102, 84),
    ULSAN_NAM("남구", 102, 84),
    ULSAN_DONG("동구", 104, 83),
    ULSAN_BUK("북구", 103, 85),
    ULSAN_ULJU("울주군", 101, 84),

    // 경기 지역
    SUWON_JANAN("수원시장안구", 60, 121),
    SUWON_GWONSEON("수원시권선구", 60, 120),
    SUWON_PALDAL("수원시팔달구", 61, 121),
    SUWON_YEONGTONG("수원시영통구", 61, 120),
    SEONGNAM_SUJEONG("성남시수정구", 63, 124),
    SEONGNAM_JUNGWON("성남시중원구", 63, 124),
    SEONGNAM_BUNDANG("성남시분당구", 62, 123),
    UIJUNGBU("의정부시", 61, 130),
    ANYANG_MANAN("안양시만안구", 59, 123),
    ANYANG_DONGAN("안양시동안구", 59, 123),
    BUCHEON_WONMI("부천시원미구", 57, 125),
    BUCHEON_SOSA("부천시소사구", 57, 125),
    BUCHEON_OJEONG("부천시오정구", 57, 126),
    GWANGMYEONG("광명시", 58, 125),
    PYEONGTAEK("평택시", 62, 114),
    DONGDUCHEON("동두천시", 61, 134),
    ANSAN_SANGROK("안산시상록구", 58, 121),
    ANSAN_DANWON("안산시단원구", 57, 121),
    GOYANG_DEOKYANG("고양시덕양구", 57, 128),
    GOYANG_ILSAN_DONG("고양시일산동구", 56, 129),
    GOYANG_ILSAN_SEO("고양시일산서구", 56, 129),
    GWACHEON("과천시", 60, 124),
    GURI("구리시", 62, 127),
    NAMYANGJU("남양주시", 64, 128),
    OSAN("오산시", 62, 118),
    SIHEUNG("시흥시", 57, 123),
    GUNPO("군포시", 59, 122),
    UIWANG("의왕시", 60, 122),
    HANAM("하남시", 64, 126),
    YONGIN_CHEOIN("용인시처인구", 64, 119),
    YONGIN_GIHEUNG("용인시기흥구", 62, 120),
    YONGIN_SUJI("용인시수지구", 62, 121),
    PAJU("파주시", 56, 131),
    ICHUN("이천시", 68, 121),
    ANSEONG("안성시", 65, 115),
    KIMPO("김포시", 55, 128),
    HWASEONG("화성시", 57, 119),
    GWAANGJU("광주시", 65, 123),
    YANGJU("양주시", 61, 131),
    POCHUN("포천시", 64, 134),
    YEOJU("여주시", 71, 121),
    YEONCHEON("연천군", 61, 138),
    GAPYEONG("가평군", 69, 133),
    YANGPYEONG("양평군", 69, 125),

    // 충청북도 지역
    CHUNGJU_SANGDANG("청주시상당구", 69, 106),
    CHUNGJU_SEOWON("청주시서원구", 69, 107),
    CHUNGJU_HEUNGDEOK("청주시흥덕구", 67, 106),
    CHUNGJU_CHEONGWON("청주시청원구", 69, 107),
    CHUNGJU("충주시", 76, 114),
    JECHUN("제천시", 81, 118),
    BOEUN("보은군", 73, 103),
    OKCHEON("옥천군", 71, 99),
    YEONGDONG("영동군", 74, 97),
    JEUNGPHYEONG("증평군", 71, 110),
    JINCHEON("진천군", 68, 111),
    GOESEON("괴산군", 74, 111),
    EUMSEONG("음성군", 72, 113),
    DANYANG("단양군", 84, 115),

    // 충청남도 지역
    CHEONAN_DONGNAM("천안시동남구", 63, 110),
    CHEONAN_SEOBUK("천안시서북구", 63, 112),
    GONGJU("공주시", 63, 102),
    BORYEONG("보령시", 54, 100),
    ASAN("아산시", 60, 110),
    SEOSAN("서산시", 51, 110),
    NONSAN("논산시", 62, 97),
    GYERYONG("계룡시", 65, 99),
    DANGJIN("당진시", 54, 112),
    GEUMSAN("금산군", 69, 95),
    BUYEO("부여군", 59, 99),
    SEOCHEON("서천군", 55, 94),
    CHEONGYANG("청양군", 57, 103),
    HONGSEONG("홍성군", 55, 106),
    YESAN("예산군", 58, 107),
    TAEAN("태안군", 48, 109),

    MOKPO("목포시", 50, 67),
    YEOSU("여수시", 73, 66),
    SUNCHEON("순천시", 70, 70),
    NAJU("나주시", 56, 71),
    GWANGYANG("광양시", 73, 70),
    DAMYANG("담양군", 61, 78),
    GOKSEONG("곡성군", 66, 77),
    GURAE("구례군", 69, 75),
    GOHEUNG("고흥군", 66, 62),
    BOSEONG("보성군", 62, 66),
    HWASUN("화순군", 61, 72),
    JANGHEUNG("장흥군", 59, 64),
    GANGJIN("강진군", 57, 63),
    HAENAM("해남군", 54, 61),
    YEONGAM("영암군", 56, 66),
    MUAN("무안군", 52, 71),
    HAMPYEONG("함평군", 52, 72),
    YEONGGWANG("영광군", 52, 77),
    JANGSEONG("장성군", 57, 77),
    WANDO("완도군", 57, 56),
    JINDO("진도군", 48, 59),
    SINAN("신안군", 50, 66),

    // 경상북도 지역
    POHANG_NAM("포항시남구", 102, 94),
    POHANG_BUK("포항시북구", 102, 95),
    GYEONGJU("경주시", 100, 91),
    KIMCHEON("김천시", 80, 96),
    ANDONG("안동시", 91, 106),
    GUMI("구미시", 84, 96),
    YEONGJU("영주시", 89, 111),
    YEONGCHEON("영천시", 95, 93),
    SANGJU("상주시", 81, 102),
    MUNGYEONG("문경시", 81, 106),
    GYEONGSAN("경산시", 91, 90),
    UISEONG("의성군", 90, 101),
    CHEONGSONG("청송군", 96, 103),
    YEONGYANG("영양군", 97, 108),
    YEONGDEOK("영덕군", 102, 103),
    CHEONGDO("청도군", 91, 86),
    GORYEONG("고령군", 83, 87),
    SEONGJU("성주군", 83, 91),
    CHILGOK("칠곡군", 85, 93),
    YECHEON("예천군", 86, 107),
    BONGHWA("봉화군", 90, 113),
    ULJIN("울진군", 102, 115),
    ULLUNG("울릉군", 127, 127),

    // 경상남도 지역
    CHANGWON_UICHA("창원시의창구", 90, 77),
    CHANGWON_SEONGSAN("창원시성산구", 91, 76),
    CHANGWON_MASAN_HAPPO("창원시마산합포구", 89, 76),
    CHANGWON_MASAN_HWIAN("창원시마산회원구", 89, 76),
    CHANGWON_JINHAE("창원시진해구", 91, 75),
    JINJU("진주시", 81, 75),
    TONGYEONG("통영시", 87, 68),
    SACHAEON("사천시", 80, 71),
    GIMHAE("김해시", 95, 77),
    MILYANG("밀양시", 92, 83),
    GEOJE("거제시", 90, 69),
    YANGSAN("양산시", 97, 79),
    UIRYEONG("의령군", 83, 78),
    HAMAN("함안군", 86, 77),
    CHANGNYEONG("창녕군", 87, 83),
    GOESEONG("고성군", 85, 71),
    NAMHAE("남해군", 77, 68),
    HADONG("하동군", 74, 73),
    SANCHEONG("산청군", 76, 80),
    HAMYANG("함양군", 74, 82),
    GEOCHANG("거창군", 77, 86),
    HAPCHEON("합천군", 81, 84),

    // 제주특별자치도 지역
    JEJU_JEJU("제주시", 53, 38),
    SEOGWIPO("서귀포시", 52, 33),

    // 강원특별자치도 지역
    CHUNCHEON("춘천시", 73, 134),
    WONJU("원주시", 76, 122),
    GANGNEUNG("강릉시", 92, 131),
    DONGHAE("동해시", 97, 127),
    TAEBEK("태백시", 95, 119),
    SOCKCHO("속초시", 87, 141),
    SAMCHEOK("삼척시", 98, 125),
    HONGCHEON("홍천군", 75, 130),
    HWANGSEONG("횡성군", 77, 125),
    YEONGWOL("영월군", 86, 119),
    PYEONGCHANG("평창군", 84, 123),
    JEONGSEON("정선군", 89, 123),
    CHEORWON("철원군", 65, 139),
    HWACHEON("화천군", 72, 139),
    YANGGU("양구군", 77, 139),
    INJE("인제군", 80, 138),
    GOSEONG_GW("고성군", 85, 145),
    YANGYANG("양양군", 88, 138),

    // 전라북도 지역
    JEONJU_WANSAN("전주시완산구", 63, 89),
    JEONJU_DEOKJIN("전주시덕진구", 63, 89),
    GUNSAN("군산시", 56, 92),
    IKSAN("익산시", 60, 91),
    JEONGEUP("정읍시", 58, 83),
    NAMWON("남원시", 68, 80),
    KIMJE("김제시", 59, 88),
    WANJU("완주군", 63, 89),
    JINAN("진안군", 68, 88),
    MUJU("무주군", 72, 93),
    JANGSU("장수군", 70, 85),
    IMSIL("임실군", 66, 84),
    SUNCHANG("순창군", 63, 79),
    GOCHANG("고창군", 56, 80),
    BUAN("부안군", 56, 87),

    // 서울특별시
    SEOUL("서울특별시", 60, 127),
    // 부산광역시
    BUSAN("부산광역시", 98, 76),
    // 대구광역시
    DAEGU("대구광역시", 89, 90),
    // 인천광역시
    INCHEON("인천광역시", 55, 124),
    // 광주광역시
    GWANGJU("광주광역시", 58, 74),
    // 대전광역시
    DAEJEON("대전광역시", 67, 100),
    // 울산광역시
    ULSAN("울산광역시", 102, 84),
    // 세종특별자치시
    SEJONG("세종특별자치시", 66, 103),
    // 경기도
    GYEONGGI("경기도", 60, 120),
    // 충청북도
    CHUNGCHEONGBUK("충청북도", 69, 107),
    // 충청남도
    CHUNGCHEONGNAM("충청남도", 68, 100),
    // 전라남도
    JEOLLANAM("전라남도", 51, 67),
    // 경상북도
    GYEONGSANGBUK("경상북도", 87, 106),
    // 경상남도
    GYEONGSANGNAM("경상남도", 91, 77),
    // 제주특별자치도
    JEJU("제주특별자치도", 52, 38),
    // 이어도
    IEODO("이어도", 28, 8),
    // 강원특별자치도
    GANGWON("강원특별자치도", 73, 134),
    // 전북특별자치도
    JEOLLABUK("전북특별자치도", 63, 89)
}





