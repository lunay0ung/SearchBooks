package com.luna.searchbooks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luna.searchbooks.model.Book
import com.luna.searchbooks.model.BookDataSource
import com.luna.searchbooks.model.BookDataSourceFactory

class BookListViewModel(
    val keyword: String
) : ViewModel() {

    private val TAG = BookListViewModel::class.java.simpleName
    var bookPagedList: LiveData<PagedList<Book>>
    private var liveDataSource: LiveData<BookDataSource>

    init {
        val itemDataSourceFactory = BookDataSourceFactory(keyword)
        Log.d(TAG, ">>> keyword: $keyword")

        liveDataSource = itemDataSourceFactory.bookLiveDataSource
        Log.d(TAG, ">>> liveDataSource: $liveDataSource")


        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(BookDataSource.PAGE_SIZE)
            .build()

        Log.d(TAG, ">>> config pageSize: ${config.pageSize}")

        bookPagedList = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()

        Log.d(TAG, ">>> bookPagedList: ${bookPagedList.value}")

    }
}