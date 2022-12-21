package com.example.android.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class BottomFragment : Fragment(R.layout.textview_layout) {
//    var mCurrentPosition = -1
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // If activity recreated (such as from screen rotate), restore
//        // the previous article selection set by onSaveInstanceState().
//        // This is primarily necessary when in the two-pane layout.
//        if (savedInstanceState != null) {
//            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION)
//            Log.i("mytag","load "+mCurrentPosition.toString())
//
//        }
//        return super.onCreateView(inflater, container, savedInstanceState)
//
//    }
//
//    fun updateArticleView(position: Int) {
//        val article = activity?.findViewById<TextView>(R.id.article)
//        //Log.i("mytag","update "+position.toString())
//        article?.text = Ipsum.Articles[position]
//        mCurrentPosition = position
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        // Save the current article selection in case we need to recreate the fragment
//        outState.putInt(ARG_POSITION, mCurrentPosition)
//        //Log.i("mytag","save "+mCurrentPosition.toString())
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        if(mCurrentPosition>=0) updateArticleView(mCurrentPosition)
//    }
//    companion object {
//        const val ARG_POSITION = "position"
//    }
}