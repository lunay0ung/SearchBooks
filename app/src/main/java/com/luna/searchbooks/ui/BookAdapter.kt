package com.luna.searchbooks.ui

import android.content.Context
import android.util.Log
import android.util.LogPrinter
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
import java.text.NumberFormat

class BookAdapter(
    private val context: Context,
    private val bookList: PagedList<Book>,
    private val bookClickListner: BookListFragment.OnBookSelected
) : PagedListAdapter<Book, BookAdapter.BookViewHolder>(BOOK_COMPARATOR) {

    private val TAG = BookAdapter::class.java.simpleName


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)

        return BookViewHolder(view)
    }
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val book = getItem(position)
        holder.itemView.setOnClickListener {
            bookClickListner.onBookSelected(book!!)
        }
        book?.let { holder.bind(it) }
    }


    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = BookViewHolder::class.java.simpleName
        private val thumb = view.thumbnail
        private val title = view.title
        private val salePrice = view.price
        private val author = view.author
        private val publisher = view.publisher
        private val translator = view.translator
        private val currencyFormat = NumberFormat.getCurrencyInstance()
        private val status = view.status

        fun bind(book: Book) {

            Glide.with(thumb.context)
                .load(book.thumbnail)
                .placeholder(R.drawable.ic_defaultimage)
                .into(thumb)

            val formattedSalePrice = currencyFormat.format(book.sale_price)
            val formattedPrice = currencyFormat.format(book.price)
            val authors = book.authors!!
                .joinToString(separator = ", ")

            title.text = book.title
            salePrice.text = formattedSalePrice+" (정가: ${formattedPrice})"
            publisher.text = book.publisher
            author.text = authors
            if(!book.translators.isNullOrEmpty()) {
                val translators = book.translators!!
                    .joinToString(separator = ", ")
                translator.text = "| $translators"
            }

            status.text = book.status
        }
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