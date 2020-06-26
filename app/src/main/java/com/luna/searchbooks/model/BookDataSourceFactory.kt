package com.luna.searchbooks.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class BookDataSourceFactory(
    context: Context,
    var keyword: String
) : DataSource.Factory<Int, Book>() {

    val bookLiveDataSource = MutableLiveData<BookDataSource>()
    val mCtx = context
    override fun create(): DataSource<Int, Book> {
        val bookDataSource = BookDataSource(mCtx, keyword)
        bookLiveDataSource.postValue(bookDataSource)
        return bookDataSource
    }
}