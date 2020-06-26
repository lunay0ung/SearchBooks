package com.luna.searchbooks.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchbooks.R
import com.luna.searchbooks.model.Book
import kotlinx.android.synthetic.main.book_list_item.view.*

class BookAdapter(
    private val context: Context,
    private val bookList: PagedList<Book>,
    private val bookClickListner: OnItemClickListener
) : PagedListAdapter<Book, BookAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    private val TAG = BookAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)

        Log.d(TAG, ">>> onCreateViewHolder")
        return BookViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val book = getItem(position)
        holder.itemView.setOnClickListener {
            bookClickListner.onClickBook(book!!)
        }
        Log.d(TAG, ">>> onBindViewHolder book")
        book?.let { holder.bind(it) }
    }


    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = BookViewHolder::class.java.simpleName
        private val thumb = view.thumbnail
        private val title = view.title
        private val price = view.price

        fun bind(book: Book) {
            Log.d(TAG, ">>> BookViewModel book: ${book.title}")
            title.text = book.title
            price.text = book.price.toString()

            Glide.with(thumb.context)
                .load(book.thumbnail)
                .placeholder(R.drawable.ic_search)
                .into(thumb);
        }
    }

    //상품 클릭 시 동작할 리스너 인터페이스 -> viewmodel이나 activity에서 구현해 사용 가능
    interface OnItemClickListener {
        fun onClickBook(book: Book)
    }


    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
                oldItem.isbn == newItem.isbn
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean =
                newItem.isbn == oldItem.isbn
        }
    }
}