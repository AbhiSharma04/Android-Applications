package com.example.android.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class TopFragment : ListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = android.R.layout.simple_list_item_activated_1

        // Create an array adapter for the list view, using the Ipsum headlines array
        listAdapter = ArrayAdapter(requireActivity(), layout, Ipsum.Headlines)
    }

    override fun onStart() {
        super.onStart()
        if (parentFragmentManager.findFragmentById(R.id.article_fragment) != null) {
            listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        }
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        // Notify the parent activity of selected item

        (activity as MainActivity).onArticleSelected(position)

        // Set the item as checked to be highlighted
        listView.setItemChecked(position, true)
    }
}