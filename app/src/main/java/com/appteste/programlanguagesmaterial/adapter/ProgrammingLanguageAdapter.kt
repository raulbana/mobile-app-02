package com.appteste.programlanguagesmaterial.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appteste.programlanguagesmaterial.R // Make sure this import is correct
import com.appteste.programlanguagesmaterial.model.ProgrammingLanguage
import java.net.URL

class ProgrammingLanguageAdapter(
    private val context: Context,
    private val languages: List<ProgrammingLanguage>
) : BaseAdapter() {

    override fun getCount(): Int = languages.size

    override fun getItem(position: Int): ProgrammingLanguage = languages[position]

    override fun getItemId(position: Int): Long = languages[position].id.toLong() // Assuming 'id' is unique

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false)
            viewHolder = ViewHolder(
                view.findViewById(R.id.imageView),
                view.findViewById(R.id.tvLanguageName),
                view.findViewById(R.id.tvLanguageDescription)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val language = getItem(position)

        viewHolder.tvName.text = language.name
        viewHolder.tvDescription.text = language.paradigm

        when (val image = language.imageUrl) {
            is Int -> viewHolder.imageView.setImageResource(image)
            is String -> {
                try {
                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                    StrictMode.setThreadPolicy(policy)
                    val inputStream = URL(image).openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    viewHolder.imageView.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    viewHolder.imageView.setImageResource(R.drawable.ic_language_placeholder)
                }
            }
            else -> viewHolder.imageView.setImageResource(R.drawable.ic_language_placeholder)
        }

        return view
    }


    private class ViewHolder(
        val imageView: ImageView,
        val tvName: TextView,
        val tvDescription: TextView
    )
}