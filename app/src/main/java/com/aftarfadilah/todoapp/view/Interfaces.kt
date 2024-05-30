package com.aftarfadilah.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.aftarfadilah.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onCheckedChanged(cb: CompoundButton,
                         isChecked:Boolean,
                         obj: Todo
    )
}

interface TodoEditClickListener {
    fun onTodoEditClick(v: View)
}

interface TodoSaveChangeListener {
    fun onTodoSaveChangesClick(v: View, obj: Todo)
}

interface RadioClickListener {
    fun onRadioClick(v:View, priority:Int, obj:Todo)
}
