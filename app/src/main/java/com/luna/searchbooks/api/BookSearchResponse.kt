package com.luna.searchbooks.api

import com.google.gson.annotations.SerializedName
import com.luna.searchbooks.model.Book
import com.luna.searchbooks.model.Meta

class BookSearchResponse {
    @field: SerializedName("meta")
    var meta: Meta? = null
    @field:SerializedName("documents")
    var books: List<Book>? = null
}

/*
response example --
{
  "meta": {
    "total_count": 897323,
    "pageable_count": 775,
    "is_end": false
  },
  "documents": [
    {
      "datetime": "2017-06-14T00:00:00.000+09:00",
      "contents": "이름 <b>이효리</b>(李孝利) 출생 1979년 5월 10일, 충청북도 청주시 흥덕구 오송읍[1] 나이 39세 (만 38세) 본관 광주 이씨 [2] 신체 164cm, 53kg, A형 학력 서울동작초등학교 서문여자중학교 서문여자고등학교...",
      "title": "<b>이효리</b> - 나무위키",
      "url": "https://namu.wiki/w/%EC%9D%B4%ED%9A%A8%EB%A6%AC"
    },
    ...
  ]
}
* */