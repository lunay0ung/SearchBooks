package com.luna.searchbooks.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luna.searchbooks.R
import com.luna.searchbooks.databinding.BookDetailFragmentBinding
import com.luna.searchbooks.model.Book
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BookDetailFragment: Fragment() {

    private val TAG = BookDetailFragment::class.java.simpleName

    private lateinit var book: Book
    private lateinit var thumbnail: ImageView
    private lateinit var author: TextView
    private lateinit var translator: TextView
    private lateinit var price: TextView
    private lateinit var date: TextView
    private lateinit var isbn: TextView
    private lateinit var more: TextView

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
        val binding =
            BookDetailFragmentBinding.inflate(inflater, container, false)
        book = arguments!!.getSerializable(BOOKMODEL) as Book

        binding.bookModel = book
        thumbnail = binding.thumbnail
        author = binding.author
        translator = binding.translator
        price = binding.price
        date = binding.date
        isbn = binding.isbn

        Glide.with(context!!)
            .load(book.thumbnail)
            .placeholder(R.drawable.ic_search)
            .into(thumbnail)

        val currencyFormat = NumberFormat.getCurrencyInstance()
        val formattedSalePrice = currencyFormat.format(book.sale_price)
        val formattedPrice = currencyFormat.format(book.price)
        val authors = book.authors!!
            .joinToString(separator = ", ")
        author.text = authors
        if(!book.translators.isNullOrEmpty()) {
            val translators = book.translators!!
                .joinToString(separator = ", ")
            translator.text = "| $translators"
        }
        price.text = formattedSalePrice+" (정가: ${formattedPrice})"
        isbn.text = "ISBN: ${book.isbn}"

        //[YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
        //2013-09-27T00:00:00.000+09:00 <- 받아오는 값
        val dtStart = book.datetime
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.KOREA)
        try {
            val bookdate: Date = format.parse(dtStart)
            val realDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(bookdate)
            date.text = realDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        more = binding.moreBtn
        more.setOnClickListener({ v -> book.url?.let { openNewTabWindow(it, this.context!!) } })

        return binding.root
    }

    fun openNewTabWindow(url: String, context : Context) {
        val uris = Uri.parse(url)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        context.startActivity(intents)
    }

}