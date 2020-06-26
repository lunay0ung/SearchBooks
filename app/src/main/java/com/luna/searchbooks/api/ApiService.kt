package com.luna.searchbooks.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/*
공식문서: https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide
GET /v2/search/web HTTP/1.1
Host: dapi.kakao.com
Authorization: KakaoAK {app_key}
* */
interface ApiService {
    @Headers("Authorization: KakaoAK 681e71063792e48eda15ca9c1c0fcb22")
    @GET("/v3/search/book")
    fun searchBooks(
        @Query("page") page: Int,
        @Query("query") keyword: String
        ) : Call<BookSearchResponse>
}