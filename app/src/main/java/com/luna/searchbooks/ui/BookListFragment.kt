package com.luna.searchbooks.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luna.searchbooks.R
import com.luna.searchbooks.model.Book

class BookListFragment : Fragment() {

    private val TAG = BookListFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    private lateinit var bookListViewModel: BookListViewModel
    private lateinit var adapter: BookAdapter
    private lateinit var bookClickListener: OnBookSelected
   // private lateinit var toolbar: Toolbar

   companion object {
       fun newInstance(): BookListFragment {
           return BookListFragment()
       }
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.book_list_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

       bookListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
           override fun <T : ViewModel?> create(modelClass: Class<T>): T {
               return BookListViewModel(
                   "안드로이드"
               ) as T
           }
       }).get(BookListViewModel::class.java)

        //val itemViewModel =  ViewModelProvider(this).get(BookListViewModel::class.java)
        bookListViewModel.bookPagedList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, ">> bookPagedList? ${it}")
            adapter = BookAdapter(context!!, it, bookClickListener)
            adapter.submitList(it)
            recyclerView.adapter = adapter
        })


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnBookSelected) {
            bookClickListener = context
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnBookSelected.")
        }
    }


    interface OnBookSelected {
        fun onBookSelected(book: Book)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
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
        return super.onCreateOptionsMenu(menu, inflater)
    }
}