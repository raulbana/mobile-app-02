package com.appteste.programlanguagesmaterial.controller

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.appteste.programlanguagesmaterial.R
import com.appteste.programlanguagesmaterial.dao.ProgrammingLanguageDao

class QueryActivity : AppCompatActivity() {

    private lateinit var dao: ProgrammingLanguageDao
    private lateinit var etQuery: EditText
    private lateinit var tvResults: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)

        dao = ProgrammingLanguageDao(this)
        etQuery = findViewById(R.id.etQuery)
        tvResults = findViewById(R.id.tvResults)

        etQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                search(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun search(query: String) {
        val results = if (query.isBlank()) {
            emptyList()
        } else {
            dao.getAllLanguages().filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        tvResults.text = if (results.isEmpty()) {
            "Nenhum resultado encontrado."
        } else {
            results.joinToString("\n") { it.name }
        }
    }
}