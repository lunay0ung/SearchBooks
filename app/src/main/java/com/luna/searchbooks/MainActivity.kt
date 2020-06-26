package com.luna.searchbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.luna.searchbooks.ui.BookListFragment

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    //private lateinit var toolbar: Toolbar
    private val fragmentManager = supportFragmentManager
    private val bookListFragment = BookListFragment()
   // private val secondFragment = SecondFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {

            /* Display First Fragment initially */
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.bookListFragment, bookListFragment)
            fragmentTransaction.commit()
        }

       // toolbar = findViewById(R.id.toolbar)

    }

    /**
     *
     *  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.options_menu, menu)
    val search = menu.findItem(R.id.appSearchBar)
    val searchView = search.actionView as SearchView
    searchView.queryHint = "책 제목 검색"
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
    return false
    }
    override fun onQueryTextChange(newText: String?): Boolean {
    Log.d(TAG, "검색어 변경: "+newText)
    //adapter.filter.filter(newText)
    return true
    }
    })
    return super.onCreateOptionsMenu(menu)
    }
     */

}