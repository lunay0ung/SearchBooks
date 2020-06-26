package com.luna.searchbooks.model

import com.google.gson.annotations.SerializedName
import java.util.*

/*
title	String	도서 제목
contents	String	도서 소개
url	String	도서 상세 URL
isbn	String	국제 표준 도서번호, ISBN10 또는 ISBN13
ISBN10, ISBN13 중 하나 이상 존재, 공백(' ')으로 구분
datetime	Datetime	도서 출판날짜, ISO 8601 형식
[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
authors	String[]	도서 저자 리스트 **array
publisher	String	도서 출판사
translators	String[]	도서 번역자 리스트 **array
price	Integer	도서 정가
sale_price	Integer	도서 판매가
thumbnail	String	도서 표지 미리보기 URL
status	String	도서 판매 상태 정보 (정상, 품절, 절판 등)
상황에 따라 변동 가능성이 있으므로 문자열 처리 지양, 단순 노출 요소로 활용 권장
* */
class Book {
    @field:SerializedName("title")
    var title: String? = null
    @field:SerializedName("contents")
    var contents: String? = null
    @field:SerializedName("url")
    var url: String? = null
    @field:SerializedName("isbn")
    var isbn: String? = null
    @field:SerializedName("datetime")
    var datetime: Date? = null
    @field:SerializedName("authors")
    var authors: List<String>? = null;
    //    var books: List<Book>? = null
    @field:SerializedName("publisher")
    var publisher: String? = null
    @field:SerializedName("translators")
    var translators: List<String>? = null
    @field:SerializedName("price")
    var price: Int? = 0
    @field:SerializedName("sale_price")
    var sale_price: Int? = 0
    @field:SerializedName("thumbnail")
    var thumbnail: String? = null
    @field:SerializedName("status")
    var status: String? = null
}
