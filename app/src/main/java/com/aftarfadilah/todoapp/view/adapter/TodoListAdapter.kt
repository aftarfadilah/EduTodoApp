package com.aftarfadilah.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aftarfadilah.todoapp.databinding.TodoItemLayoutBinding
import com.aftarfadilah.todoapp.model.Todo
import com.aftarfadilah.todoapp.view.fragment.TodoListFragmentDirections
import com.aftarfadilah.todoapp.view.TodoCheckedChangeListener
import com.aftarfadilah.todoapp.view.TodoEditClickListener

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick : (Todo) -> Unit)
    : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(),
    TodoCheckedChangeListener,
    TodoEditClickListener {
    class TodoViewHolder(var binding: TodoItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return TodoViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int)
    {
        holder.binding.todo = todoList[position]
        holder.binding.listener = this
        holder.binding.editListener = this
        holder.binding.checkTask.text = todoList[position].title
        holder.binding.checkTask.setOnCheckedChangeListener {
                compoundButton, b ->  adapterOnClick(todoList[position])
        }
        holder.binding.imgEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)
            Navigation.findNavController(holder.binding.root).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChanged(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(cb.isPressed) {
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodoFragment(uuid)

        Navigation.findNavController(v).navigate(action)
    }

}