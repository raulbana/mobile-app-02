package com.appteste.programlanguagesmaterial.dao

import com.appteste.programlanguagesmaterial.R
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.appteste.programlanguagesmaterial.db.DBHelper
import com.appteste.programlanguagesmaterial.model.ProgrammingLanguage

class ProgrammingLanguageDao(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun addLanguage(language: ProgrammingLanguage): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", language.name)
            put("typing", language.typing)
            put("paradigm", language.paradigm)
            put("learningCurve", language.learningCurve)
            put("description", language.description)
            if(language.imageUrl != null){
                put("imageUrl", language.imageUrl)
            }
        }

        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllLanguages(): List<ProgrammingLanguage> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null)
        val languageList = mutableListOf<ProgrammingLanguage>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val typing = cursor.getString(cursor.getColumnIndexOrThrow("typing"))
            val paradigm = cursor.getString(cursor.getColumnIndexOrThrow("paradigm"))
            val learningCurve = cursor.getString(cursor.getColumnIndexOrThrow("learningCurve"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val imageUrl = if (!cursor.isNull(cursor.getColumnIndexOrThrow("imageUrl"))) {
                cursor.getInt(cursor.getColumnIndexOrThrow("imageUrl"))
            } else {
                R.drawable.ic_language_placeholder
            }

            val language = ProgrammingLanguage(
                id = id,
                name = name,
                typing = typing,
                paradigm = paradigm,
                learningCurve = learningCurve,
                description = description,
                imageUrl = imageUrl
            )
            languageList.add(language)
        }

        cursor.close()
        db.close()
        return languageList
    }

    fun getLanguageById(id: Int): ProgrammingLanguage? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DBHelper.TABLE_NAME,
            null,
            "id=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var language: ProgrammingLanguage? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val typing = cursor.getString(cursor.getColumnIndexOrThrow("typing"))
            val paradigm = cursor.getString(cursor.getColumnIndexOrThrow("paradigm"))
            val learningCurve = cursor.getString(cursor.getColumnIndexOrThrow("learningCurve"))
            val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val imageUrl = if (!cursor.isNull(cursor.getColumnIndexOrThrow("imageUrl"))) {
                cursor.getInt(cursor.getColumnIndexOrThrow("imageUrl"))
            } else {
                R.drawable.ic_language_placeholder
            }

            language = ProgrammingLanguage(
                id = id,
                name = name,
                typing = typing,
                paradigm = paradigm,
                learningCurve = learningCurve,
                description = description,
                imageUrl = imageUrl
            )
        }

        cursor.close()
        db.close()
        return language
    }

    fun updateLanguage(language: ProgrammingLanguage): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", language.name)
            put("typing", language.typing)
            put("paradigm", language.paradigm)
            put("learningCurve", language.learningCurve)
            put("description", language.description)
            put("imageUrl", language.imageUrl)
        }

        val rowsAffected = db.update(
            DBHelper.TABLE_NAME,
            values,
            "id=?",
            arrayOf(language.id.toString())
        )
        db.close()
        return rowsAffected
    }

    fun deleteLanguage(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(DBHelper.TABLE_NAME, "id=?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }
}
