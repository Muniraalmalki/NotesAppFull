package com.example.notesappfull

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context): SQLiteOpenHelper(context,"notes.db", null, 2) {
    private val sqLiteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table notes (pk INTEGER PRIMARY KEY AUTOINCREMENT, Note text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS notes")  // This removes the table if a new version is detected
        onCreate(db)
    }

    fun saveData(note: String){
        val contentValues = ContentValues()
        // There is no need to pass in the pk because it is automatically generated
        contentValues.put("Note", note)

        sqLiteDatabase.insert("notes", null, contentValues)
    }

    fun readData(): ArrayList<Note>{
        val noteList = arrayListOf<Note>()

        // Read all data using cursor
        val cursor: Cursor = sqLiteDatabase.rawQuery("SELECT * FROM notes", null)

        if(cursor.count < 1){  // Handle empty table
            println("No Data Found")
        }else{
            while(cursor.moveToNext()){  // Iterate through table and populate people Array List
                val pk = cursor.getInt(0)  // The integer value refers to the column
                val note = cursor.getString(1)

                noteList.add(Note(pk, note))
            }
        }
        return noteList
    }

    fun updateData(note: Note){
        val contentValues = ContentValues()
        contentValues.put("Note", note.note)
        sqLiteDatabase.update("notes", contentValues, "pk = ${note.pk}", null)
    }

    fun deleteData(note: Note){
        sqLiteDatabase.delete("notes", "pk = ${note.pk}", null)
    }
}