package com.appteste.programlanguagesmaterial.controller

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.appteste.programlanguagesmaterial.R
import com.appteste.programlanguagesmaterial.adapter.ProgrammingLanguageAdapter
import com.appteste.programlanguagesmaterial.dao.ProgrammingLanguageDao
import com.appteste.programlanguagesmaterial.model.ProgrammingLanguage

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView
    private lateinit var dao: ProgrammingLanguageDao
    private lateinit var adapter: ProgrammingLanguageAdapter
    private var languages: List<ProgrammingLanguage> = emptyList()
    private var filteredLanguages: List<ProgrammingLanguage> = emptyList()

    private val mockLanguages = listOf(
        ProgrammingLanguage(
            1,
            "Java",
            "Tipado Estático",
            "Orientada a Objetos",
            "Médio",
            "Uma linguagem de programação amplamente utilizada para desenvolvimento de aplicações empresariais e móveis.",
            R.drawable.ic_logo_java
        ),
        ProgrammingLanguage(
            2,
            "Javascript",
            "Tipado Dinâmico",
            "Orientada a Objetos",
            "Fácil",
            "Uma linguagem amplamente usada para desenvolvimento web.",
            R.drawable.ic_logo_javascript
        ),
        ProgrammingLanguage(
            3,
            "Ruby",
            "Tipado Dinâmico",
            "Orientada a Objetos",
            "Médio",
            "Muito usada em startups com foco em desenvolvimento ágil.",
            R.drawable.ic_logo_ruby
        ),
        ProgrammingLanguage(
            4,
            "C#",
            "Tipado Estático",
            "Orientada a Objetos",
            "Médio",
            "Popular para desenvolvimento de aplicações Windows e jogos com Unity.",
            R.drawable.ic_logo_csharp
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvChars)
        emptyTextView = findViewById(R.id.tvEmpty)
        dao = ProgrammingLanguageDao(this)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selectedLanguage = parent.getItemAtPosition(position) as ProgrammingLanguage
            val intent = Intent(this, NewProgrammingLanguageActivity::class.java).apply {
                putExtra("languageID", selectedLanguage.id)
            }
            startActivity(intent)
        }

        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterLanguages(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onResume() {
        super.onResume()
        listAllLanguages()
    }

    private fun listAllLanguages() {
        languages = dao.getAllLanguages()

        if (languages.isEmpty()) {
            mockLanguages.forEach { dao.addLanguage(it) }
            languages = dao.getAllLanguages()
        }

        filteredLanguages = languages

        if (languages.isEmpty()) {
            listView.visibility = View.GONE
            emptyTextView.visibility = View.VISIBLE
        } else {
            listView.visibility = View.VISIBLE
            emptyTextView.visibility = View.GONE

            adapter = ProgrammingLanguageAdapter(this, filteredLanguages)
            listView.adapter = adapter
        }
    }

    private fun filterLanguages(query: String) {
        filteredLanguages = if (query.isBlank()) {
            languages
        } else {
            languages.filter {
                it.name.contains(query, ignoreCase = true)
                        || it.typing.contains(query, ignoreCase = true)
                        || it.paradigm.contains(query, ignoreCase = true)
                        || it.description.contains(query, ignoreCase = true)
            }
        }
        updateListView()
    }

    private fun updateListView() {
        adapter = ProgrammingLanguageAdapter(this, filteredLanguages)
        listView.adapter = adapter

        listView.visibility = if (filteredLanguages.isEmpty()) View.GONE else View.VISIBLE
        emptyTextView.visibility = if (filteredLanguages.isEmpty()) View.VISIBLE else View.GONE
    }

    fun addNewLanguage(view: View) {
        val intent = Intent(this, NewProgrammingLanguageActivity::class.java)
        startActivity(intent)
    }
}
