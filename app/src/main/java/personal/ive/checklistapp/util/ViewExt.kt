package personal.ive.checklistapp.util

import androidx.appcompat.widget.SearchView

/**
 * Created by ivasil on 4/6/2021
 */
 
inline fun SearchView.onQueryTextChanged(crossinline handleQueryChange: (String) -> Unit) {

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            handleQueryChange(newText.orEmpty())
            return true
        }
    })
}