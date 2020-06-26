package com.luna.searchbooks.model

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class BookDataSourceFactory(
    var keyword: String
) : DataSource.Factory<Int, Book>() {

    val bookLiveDataSource = MutableLiveData<BookDataSource>()
    override fun create(): DataSource<Int, Book> {
        val bookDataSource = BookDataSource(keyword)
        bookLiveDataSource.postValue(bookDataSource)
        return bookDataSource
    }
}