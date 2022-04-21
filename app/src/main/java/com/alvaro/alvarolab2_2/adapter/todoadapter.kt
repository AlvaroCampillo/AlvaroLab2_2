package com.alvaro.alvarolab2_2.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.alvarolab2_2.AddNewTask
import com.alvaro.alvarolab2_2.MainActivity
import com.alvaro.alvarolab2_2.R
import com.alvaro.alvarolab2_2.model.todomodel
import com.alvaro.alvarolab2_2.utilities.databasehelp


class todoadapter(myDB: databasehelp, private var activity: MainActivity): RecyclerView.Adapter<todoadapter.MyViewHolder>(){

    private var mList: List<todomodel>? = null
    private var myDB: databasehelp = TODO()
        get() {
            TODO()
        }
    fun todoadapter(myDB: databasehelp?, activity: MainActivity?) {
        this.activity = activity!!
        if (myDB != null) {
            this.myDB = myDB
        }
    }

    class MyViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mCheckBox: CheckBox
        init {
            mCheckBox = itemView.findViewById(R.id.mcheckbox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false)
        return MyViewHolder(v)
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: todomodel = mList!![position]
        holder.mCheckBox.setText(item.getTask())
        holder.mCheckBox.isChecked = toBoolean(item.getStatus())
        holder.mCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                myDB.updateStatus(item.getId(), 1)
            } else myDB.updateStatus(item.getId(), 0)
        }
        TODO("Not yet implemented")
    }

    fun toBoolean(num: Int): Boolean {
        return num != 0
    }

    fun getContext(): Context? {
        return activity
    }

    fun setTasks(mList: List<todomodel?>?) {
        this.mList = mList as List<todomodel>?
        notifyDataSetChanged()
    }

    fun deletTask(position: Int) {
        val item: todomodel = mList!![position]
        myDB.deleteTask(item.getId())
        mList.remove(position) //remove should work but it doesnt
        notifyItemRemoved(position) // this is the implementation that we need in order to delete one task
    }

    fun editItem(position: Int) { // implementation to edit one task
        val item: todomodel = mList!![position]
        val bundle = Bundle()
        bundle.putInt("id", item.getId())
        bundle.putString("task", item.getTask())
        val task = AddNewTask()
        task.setArguments(bundle)
        task.show(activity.supportFragmentManager, task.getTag())
    }

    override fun getItemCount(): Int {
        return mList!!.size
        TODO("Not yet implemented")
    }
}
