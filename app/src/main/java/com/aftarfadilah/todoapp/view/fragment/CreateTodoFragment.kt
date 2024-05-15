package com.aftarfadilah.todoapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aftarfadilah.todoapp.R
import com.aftarfadilah.todoapp.databinding.FragmentCreateTodoBinding
import com.aftarfadilah.todoapp.model.Todo
import com.aftarfadilah.todoapp.viewmodel.DetailTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var binding: FragmentCreateTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            var todo = Todo(
                binding.txtTitle.text.toString(),
                binding.txtNotes.text.toString()
            )
            val list = listOf(todo)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
}