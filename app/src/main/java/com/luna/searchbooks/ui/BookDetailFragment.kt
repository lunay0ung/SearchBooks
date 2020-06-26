package com.luna.searchbooks.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.luna.searchbooks.R
import com.luna.searchbooks.model.Book

class BookDetailFragment: Fragment() {

    private val TAG = BookDetailFragment::class.java.simpleName
    private lateinit var textview: TextView

    //2
    companion object {
        private const val BOOKMODEL = "model"
        fun newInstance(book: Book): BookDetailFragment {
            val args = Bundle()
            args.putSerializable(BOOKMODEL, book)
            val fragment = BookDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview = view.findViewById(R.id.textview)


        val book = arguments!!.getSerializable(BOOKMODEL) as Book
        Log.d(TAG, ">> 되나?? ")
        textview.text="${book.title}"

    }
}