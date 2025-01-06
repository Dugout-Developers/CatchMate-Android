package com.catchmate.domain.model.enumclass

// name 프로퍼티가 enum 기본 속성이라 생성자 명으로 지정할 수 없음 name -> teamName 대체
enum class Club(val id: Int, val teamName: String, val homeStadium: String, val region: String) {
    KIA(1, "KIA 타이거즈", "기아 챔피언스 필드", "광주광역시"),
    SAMSUNG(2, "삼성 라이온즈", "대구 삼성 라이온즈 파크", "대구광역시"),
    LG(3, "LG 트윈스", "잠실 야구장", "서울특별시"),
    DOOSAN(4, "두산 베어스", "잠실 야구장", "서울특별시"),
    KT(5, "KT 위즈", "수원KT위즈파크", "경기도 수원시"),
    SSG(6, "SSG 랜더스", "인천 SSG랜더스필드", "인천광역시"),
    LOTTE(7, "롯데 자이언츠", "사직 야구장", "부산광역시"),
    HANWHA(8, "한화 이글스", "한화생명 이글스 파크", "대전광역시"),
    NC(9, "NC 다이노스", "창원NC파크", "경상남도 창원시"),
    KIWOOM(10, "키움 히어로즈", "고척 스카이돔", "서울특별시"),
    PACIFIST(11, "평화주의자", "평화주의자", "평화주의자"),
    BEGINNER(12, "야알못", "야알못", "야알못"),
}
