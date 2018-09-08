package com.github.neefrankie.photogallery

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_photo_gallery.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.act
import java.io.IOException

class PhotoGalleryFragment : Fragment(), AnkoLogger {

    private var mItems = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        FetchItemTask().execute()

        val intent = PollService.newIntent(activity!!)
        activity?.startService(intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photo_recycler_view.layoutManager = GridLayoutManager(context, 3)
    }

    private fun setupAdapter() {
        if (isAdded) {
            photo_recycler_view.adapter = PhotoAdapter(mItems)
        }
    }

    companion object {
        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }
    }

    inner class PhotoHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view as TextView
    }

    inner class PhotoAdapter(private val items: List<String>) : RecyclerView.Adapter<PhotoHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PhotoHolder {
            val textView = TextView(activity)
            return PhotoHolder(textView)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val item = items[position]
            holder.titleTextView.text = item
        }

    }

    private class FetchItemTask : AsyncTask<Unit, Unit, List<String>>(), AnkoLogger {
        override fun doInBackground(vararg params: Unit): List<String> {

            return FlickrFetchr().fetchItems()
        }

        override fun onPostExecute(result: List<String>?) {

        }
    }


}