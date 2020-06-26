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

                    //Log.i(TAG, ">>>>> 결과? "+ apiResponse.totalCount)

                    val key = if (params.key > 1) params.key - 1 else 0
                    responseItems?.let {
                        Log.i(TAG, ">>>>> loadBefore isbn?  ${responseItems!!.get(0).isbn} ")
                        callback.onResult(responseItems, key)
                    }
                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadBefore onFailure")
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

                    Log.i(TAG, ">>>>> loadInitial 결과?  ${responseItems!!.get(0).authors!!.get(0)} ")
                    Log.i(TAG, ">>>>> loadInitial isbn?  ${responseItems!!.get(0).isbn} ")

                    Log.i(TAG, ">>>>> loadInitial meta 결과? ${meta!!.isEnd}")



                    responseItems?.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }


                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                Log.e(TAG, ">> loadInitial onFailure: ${t.message}")

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
                        Log.i(TAG, ">>>>> loadAfter isbn?  ${responseItems!!.get(0).isbn} ")

                        if(!apiResponse.meta!!.isEnd) {
                            callback.onResult(responseItems, key)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BookSearchResponse>, t: Throwable) {
                Log.e(TAG, "loadAfter onFailure")

            }
        })
    }

}
