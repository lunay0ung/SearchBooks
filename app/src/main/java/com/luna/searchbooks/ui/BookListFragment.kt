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
import androidx.paging.PagedList
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
    var searchWord: String? = "카카오"
    private lateinit var mCtx: Context

   companion object {
       fun newInstance(): BookListFragment {
           return BookListFragment()
       }

       fun newInstance(query: String): BookListFragment {
           val fragment = BookListFragment()
           val args = Bundle()
           args.putString("query", query)
           fragment.setArguments(args)
           return fragment
       }
   }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mCtx = context!!

        val args = arguments
        searchWord = args?.getString("query")

        if(searchWord.isNullOrEmpty()) {
            searchWord = "카카오"
        }

       bookListViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
           override fun <T : ViewModel?> create(modelClass: Class<T>): T {
               return BookListViewModel(
                   context!!,
                   searchWord!!
               ) as T
           }
       }).get(BookListViewModel::class.java)

        bookListViewModel.bookPagedList.observe(viewLifecycleOwner, Observer {
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
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }
}