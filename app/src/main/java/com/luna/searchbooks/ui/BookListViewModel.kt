package com.luna.searchbooks.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luna.searchbooks.model.Book
import com.luna.searchbooks.model.BookDataSource
import com.luna.searchbooks.model.BookDataSourceFactory

class BookListViewModel(
    context: Context,
    val keyword: String
) : ViewModel() {

    private val TAG = BookListViewModel::class.java.simpleName
    var bookPagedList: LiveData<PagedList<Book>>
    private var liveDataSource: LiveData<BookDataSource>
    init {
        val itemDataSourceFactory = BookDataSourceFactory(context, keyword)
        liveDataSource = itemDataSourceFactory.bookLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(BookDataSource.PAGE_SIZE)
            .build()
        bookPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()
    }
}