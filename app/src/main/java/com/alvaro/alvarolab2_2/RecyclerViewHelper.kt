package com.alvaro.alvarolab2_2

import android.R
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.alvarolab2_2.adapter.todoadapter

class RecyclerViewTouchHelper(adapter: todoadapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {


    private var adapter: todoadapter? = null

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(adapter?.getContext())
            builder.setTitle("Delete Task")
            builder.setMessage("Are You Sure ?")
            builder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> adapter?.deletTask(position) })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    adapter?.notifyItemChanged(
                        position
                    )
                })
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            adapter?.editItem(position)
        }
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // on this part we implement the code to be able to either swiper right or left each of the tasks
        // the RecyclerViewSwipeDecorator should be able to work since I added the library
        // implementation 'com.github.xabaras:RecyclerViewSwipeDecorator:1.4' but it doesnt and
        // I dont know why
        RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftBackgroundColor(
                adapter?.getContext()?.let {
                    ContextCompat.getColor(
                        it,
                        R.color.color_primary_dark //here we set a background color
                    )
                }
            )
            .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24) //create the vector assets that
            .addSwipeRightBackgroundColor(Color.RED) // will appear while swiping (delete and edit)
            .addSwipeRightActionIcon(R.drawable.ic_baseline_delete)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


    init {
        this.adapter = adapter
    }
}