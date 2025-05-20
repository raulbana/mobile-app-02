package com.appteste.programlanguagesmaterial.controller

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.appteste.programlanguagesmaterial.R
import com.appteste.programlanguagesmaterial.dao.ProgrammingLanguageDao
import com.appteste.programlanguagesmaterial.model.ProgrammingLanguage

class NewProgrammingLanguageActivity : AppCompatActivity() {

    private lateinit var dao: ProgrammingLanguageDao
    private var languageId: Int = 0

    private val inputs by lazy {
        mapOf(
            "name" to findViewById<EditText>(R.id.etName),
            "typing" to findViewById<EditText>(R.id.etTyping),
            "paradigm" to findViewById<EditText>(R.id.etParadigm),
            "learningCurve" to findViewById<EditText>(R.id.etLearningCurve),
            "description" to findViewById<EditText>(R.id.etDescription),
            "imageUrl" to findViewById<EditText>(R.id.etImageURL)
        )
    }

    private val btnDelete by lazy { findViewById<Button>(R.id.btnDelete) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_programming_language)

        dao = ProgrammingLanguageDao(this)

        languageId = intent.getIntExtra("languageID", 0)

        if (languageId != 0) {
            btnDelete.visibility = View.VISIBLE
            loadLanguage()
        } else {
            btnDelete.visibility = View.GONE
        }
    }

    fun saveLanguage(view: View) {
    if (areFieldsValid()) {
        val imageUrlText = inputs["imageUrl"]!!.text.toString()
        val imageUrlInt = imageUrlText.toIntOrNull()
        val imageUrlFinal = imageUrlInt ?: R.drawable.ic_language_placeholder

        val language = ProgrammingLanguage(
            id = languageId.takeIf { it != 0 } ?: 0,
            name = inputs["name"]!!.text.toString(),
            typing = inputs["typing"]!!.text.toString(),
            paradigm = inputs["paradigm"]!!.text.toString(),
            learningCurve = inputs["learningCurve"]!!.text.toString(),
            description = inputs["description"]!!.text.toString(),
            imageUrl = imageUrlFinal
        )

        if (languageId == 0) {
            dao.addLanguage(language)
            showToast("Linguagem salva com sucesso!")
        } else {
            dao.updateLanguage(language)
            showToast("Linguagem atualizada com sucesso!")
        }

        finish()
    } else {
        showToast("Preencha todos os campos!")
    }
    }

  private fun loadLanguage() {
    dao.getLanguageById(languageId)?.let { lang ->
        inputs["name"]?.setText(lang.name)
        inputs["typing"]?.setText(lang.typing)
        inputs["paradigm"]?.setText(lang.paradigm)
        inputs["learningCurve"]?.setText(lang.learningCurve)
        inputs["description"]?.setText(lang.description)
        inputs["imageUrl"]?.setText(lang.imageUrl?.toString() ?: "")

        val imageView = findViewById<ImageView>(R.id.ivLanguageImage)
        val imageRes = lang.imageUrl ?: R.drawable.ic_language_placeholder
        imageView.setImageResource(imageRes)
    }
}

    fun deleteLanguage(view: View) {
        dao.deleteLanguage(languageId)
        showToast("Linguagem excluída com sucesso!")
        finish()
    }

    private fun areFieldsValid(): Boolean {
        return inputs.filterKeys { it != "imageUrl" }.values.all { it.text.isNotBlank() }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
