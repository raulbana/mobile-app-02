package com.appteste.programlanguagesmaterial.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
        companion object {
            const val DATABASE_NAME = "programming_languages.db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "languages"
        }

        override fun onCreate(db: SQLiteDatabase){
            val createTable = """
                CREATE TABLE IF NOT EXISTS $TABLE_NAME(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    typing TEXT,
                    paradigm TEXT,
                    learningCurve TEXT,
                    description TEXT,
                    imageUrl TEXT
                ) 
            """.trimIndent()
            db.execSQL(createTable)
        }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}

