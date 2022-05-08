package com.app.teambrite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION)  {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                TEXT1_COl + " TEXT," +
                TEXT2_COl + " TEXT," +
                TEXT3_COl + " TEXT," +
                TEXT4_COl + " TEXT" + ")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addName(text1 : String, text2 : String ,text3 : String, text4 : String){
        val values = ContentValues()

        values.put(TEXT1_COl, text1)
        values.put(TEXT2_COl, text2)
        values.put(TEXT3_COl, text3)
        values.put(TEXT4_COl, text4)

        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getNameList(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    companion object{
        private val DATABASE_NAME = "COVID19"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "covid_data"

        val ID_COL = "id"
        val TEXT1_COl = "text1"
        val TEXT2_COl = "text2"
        val TEXT3_COl = "text3"
        val TEXT4_COl = "text4"
    }
}