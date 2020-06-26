package com.luna.searchbooks.model

import android.util.Log

import androidx.paging.PageKeyedDataSource
import com.luna.searchbooks.api.ApiService
import com.luna.searchbooks.api.ApiServiceBuilder
import com.luna.searchbooks.api.BookSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookDataSource(
    val keyword: String
) : PageKeyedDataSource<Int, Book>() {

    private val TAG = BookDataSource::class.java.simpleName

    companion object {
        const val FIRST_PAGE = 1
        const val PAGE_SIZE = 50
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchBooks(params.key, keyword)
        call.enqueue(object : Callback<BookSearchResponse> {
            override fun onResponse(call: Call<BookSearchResponse>, response: Response<BookSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.books
                    val key = if (params.key > 1) params.key - 1 else 0
                    responseItems?.let {
                        callback.onResult(responseItems, key)
                    }
                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadBefore onFailure: ${t.message}")
            }
        })
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchBooks(FIRST_PAGE, keyword)
        call.enqueue(object : Callback<BookSearchResponse> {
            override fun onResponse(call: Call<BookSearchResponse>, response: Response<BookSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.books
                    val meta = apiResponse.meta
                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }
                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                //todo 검색결과가 없을 경우에 대비한 예외처리 필요
                Log.e(TAG, "loadInitial onFailure: ${t.message}")
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        val service = ApiServiceBuilder.buildService(ApiService::class.java)
        val call = service.searchBooks(params.key, keyword)
        call.enqueue(object : Callback<BookSearchResponse> {
            override fun onResponse(call: Call<BookSearchResponse>, response: Response<BookSearchResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()!!
                    val responseItems = apiResponse.books
                    val key = params.key + 1
                    responseItems?.let {
                        if(!apiResponse.meta!!.isEnd) {
                            callback.onResult(responseItems, key)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadAfter onFailure: ${t.message}")
            }
        })
    }

}
