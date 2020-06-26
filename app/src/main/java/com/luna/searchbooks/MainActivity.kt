package com.luna.searchbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.luna.searchbooks.model.Book
import com.luna.searchbooks.ui.BookDetailFragment
import com.luna.searchbooks.ui.BookListFragment

class MainActivity : AppCompatActivity(), BookListFragment.OnBookSelected {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var searchWord : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, BookListFragment.newInstance(), "bookList")
                .commit()
        }
    }

    override fun onBookSelected(book: Book) {
        val detailsFragment =
            BookDetailFragment.newInstance(book)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, "bookDetails")
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "책 제목 검색"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrBlank()) {
                    Log.d(TAG, "검색 시작: $query")
                    BookListFragment.SHOW_SEARCH_RESULT_MESSAGE = true
                    searchWord = query
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.root_layout, BookListFragment.newInstance(query!!), "bookList")
                        .commit()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "검색어 변경: "+newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

}