package com.nourtayeb.movies_mvi.common.utility

import android.widget.SearchView

fun SearchView.OnQueryTextListener(callback : (String) -> Unit ) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let { callback(it) }
            return false
        }
    })
}
