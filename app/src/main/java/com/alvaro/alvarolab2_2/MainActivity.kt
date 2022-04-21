package com.alvaro.alvarolab2_2

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.alvarolab2_2.adapter.todoadapter
import com.alvaro.alvarolab2_2.model.todomodel
import com.alvaro.alvarolab2_2.utilities.databasehelp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity() {
    var mRecyclerview: RecyclerView? = null // we define different variables that we will need
    var fab: FloatingActionButton? = null
    var myDB: databasehelp? = null
    var mList: List<todomodel?>? = null
    var adapter: todoadapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerview = findViewById(R.id.recyclerview)
        fab = findViewById(R.id.fab)

        myDB = databasehelp(this@MainActivity)
        mList = ArrayList<todomodel?>()
        adapter = todoadapter(myDB!!, this@MainActivity)

        mRecyclerview?.setHasFixedSize(true)
        mRecyclerview?.setLayoutManager(LinearLayoutManager(this))
        mRecyclerview?.setAdapter(adapter)

        mList = myDB!!.getAllTasks()
        Collections.reverse(mList)
        adapter!!.setTasks(mList)

        fab?.setOnClickListener(View.OnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }) // on this part we need to create a base configurtaion for the
        // tasks. We assign a new instance each time. Dont know why newInstance is not working properly
        //since I believe everything is alright.
        val itemTouchHelper = ItemTouchHelper(RecyclerViewTouchHelper(adapter!!))
        itemTouchHelper.attachToRecyclerView(mRecyclerview)

    }


    fun onDialogClose(dialogInterface: DialogInterface?) {
        mList = myDB!!.getAllTasks()
        Collections.reverse(mList)
        adapter?.setTasks(mList)
        adapter?.notifyDataSetChanged()
    }
}
