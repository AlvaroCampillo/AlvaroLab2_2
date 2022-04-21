package com.alvaro.alvarolab2_2

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.alvaro.alvarolab2_2.model.todomodel
import com.alvaro.alvarolab2_2.utilities.databasehelp
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTask : BottomSheetDialogFragment() {

    object TAG {
        val TAG = "AddNewTask"
    }

    private var mEditText: EditText? = null
    private var mSaveButton: Button? = null
    private var myDb: databasehelp? = null

    fun newInstance(): AddNewTask {
        return AddNewTask()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.addnewtask, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mEditText = view.findViewById(R.id.edittext)
        mSaveButton = view.findViewById(R.id.button_save)

        myDb = databasehelp(getActivity())
        var isUpdate = false
        val bundle: Bundle? = getArguments()
        if (bundle != null) {
            isUpdate = true
            val task: String? = bundle.getString("task")
            mEditText?.setText(task)
            if (task?.length!! > 0) {
                mSaveButton?.setEnabled(false)
            }
        }
        mEditText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (s.toString() == "") {
                        mSaveButton?.setEnabled(false)
                        mSaveButton?.setBackgroundColor(Color.GRAY)
                    } else {
                        mSaveButton?.setEnabled(true)
                        mSaveButton?.setBackgroundColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary_dark))
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
            val finalIsUpdate = isUpdate
            mSaveButton?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val text: String = mEditText?.getText().toString()
                    if (finalIsUpdate) {
                        myDb!!.updateTask(bundle!!.getInt("id"), text)
                    } else {
                        val item = todomodel()
                        item.setTask(text)
                        item.setStatus(0)
                        myDb!!.insertTask(item)
                    }
                    dismiss()
                }
            })
            fun onDismiss(@NonNull dialog: DialogInterface?) {
                if (dialog != null) {
                    super.onDismiss(dialog)
                }
                val activity: FragmentActivity? = getActivity()
                if (activity is OnDialogCloseListner) {
                    (activity as OnDialogCloseListner).onDialogClose(dialog)
                }
            }

    }
}



