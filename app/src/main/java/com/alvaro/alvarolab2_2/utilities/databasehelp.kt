package com.alvaro.alvarolab2_2.utilities

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alvaro.alvarolab2_2.model.todomodel


class databasehelp(
    context: Context?,

    DATABASE_NAME: String = "TODO_DATABASE"

) : SQLiteOpenHelper(context,DATABASE_NAME, null, 1) {

    private var db: SQLiteDatabase? = null
    private val DATABASE_NAME = "TODO_DATABASE"
    private val TABLE_NAME = "TODO_TABLE"
    private val COL_1 = "ID"
    private val COL_2 = "TASK"
    private val COL_3 = "STATUS"


    override fun onCreate(p0: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
        TODO("Not yet implemented")
    }

    fun insertTask(model: todomodel) {
        db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_2, model.getTask())
        values.put(COL_3, 0)
        db!!.insert(TABLE_NAME, null, values)
    }

    fun updateTask(id: Int, task: String?) {
        db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_2, task)
        db!!.update(TABLE_NAME, values, "ID=?", arrayOf(id.toString()))
    }

    fun updateStatus(id: Int, status: Int) {
        db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_3, status)
        db!!.update(TABLE_NAME, values, "ID=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        db = this.writableDatabase
        db!!.delete(TABLE_NAME, "ID=?", arrayOf(id.toString()))
    }

    @SuppressLint("Range")
    fun getAllTasks(): List<todomodel>? {
        db = this.writableDatabase
        var cursor: Cursor? = null
        val modelList: MutableList<todomodel> = ArrayList<todomodel>()
        db!!.beginTransaction()
        try {
            cursor = db!!.query(TABLE_NAME, null, null, null, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        val task = todomodel()
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)))
                        task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)))
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)))
                        modelList.add(task)
                    } while (cursor.moveToNext())
                }
            }
        } finally {
            db!!.endTransaction()
            cursor!!.close()
        }
        return modelList
    }

}