package com.example.umc_wireframe.domain.model

enum class MidTermRegion(val regId: String, val regionName: String) {
    SEOUL("11B10101", "서울"),
    SEOGWIPO("11G00401", "서귀포"),
    INCHEON("11B20201", "인천"),
    GWANGJU("11F20501", "광주"),
    SUWON("11B20601", "수원"),
    MOKPO("21F20801", "목포"),
    PAJU("11B20305", "파주"),
    YEOSU("11F20401", "여수"),
    CHUNCHEON("11D10301", "춘천"),
    JEONJU("11F10201", "전주"),
    WONJU("11D10401", "원주"),
    GUNSAN("21F10501", "군산"),
    GANGNEUNG("11D20501", "강릉"),
    BUSAN("11H20201", "부산"),
    DAEJEON("11C20401", "대전"),
    ULSAN("11H20101", "울산"),
    SEOSAN("11C20101", "서산"),
    CHANGWON("11H20301", "창원"),
    SEJONG("11C20404", "세종"),
    DAEGU("11H10701", "대구"),
    CHEONGJU("11C10301", "청주"),
    ANDONG("11H10501", "안동"),
    JEJU("11G00201", "제주"),
    POHANG("11H10201", "포항");

    companion object {
        fun findRegion(regionName: String): MidTermRegion? {
            return values().find { it.regionName == regionName }
        }
    }
}