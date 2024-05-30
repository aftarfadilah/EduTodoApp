package com.aftarfadilah.todoapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.aftarfadilah.todoapp.databinding.FragmentEditTodoBinding
import com.aftarfadilah.todoapp.model.Todo
import com.aftarfadilah.todoapp.view.RadioClickListener
import com.aftarfadilah.todoapp.view.TodoSaveChangeListener
import com.aftarfadilah.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment :
    Fragment(),
    RadioClickListener,
    TodoSaveChangeListener {
//    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewModel: DetailTodoViewModel
    private lateinit var binding: FragmentEditTodoBinding
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditTodoBinding.inflate(
            inflater, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid

        binding.radioListener = this

        binding.txtJudulTodo.text = "Edit Todo"
        binding.btnAdd.text = "Save Changes"

        viewModel.fetch(uuid)
        observeViewModel()

        binding.btnAdd.setOnClickListener {
            val radio =
                view.findViewById<RadioButton>(binding.radioGroupPriority.checkedRadioButtonId)
            viewModel.update(binding.txtTitle.text.toString(),
                binding.txtNotes.text.toString(), radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }


    }


    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            binding.todo = it
            binding.radioListener = this
            binding.saveListener = this

            when (it.priority) {
                1 -> binding.radioLow.isChecked = true
                2 -> binding.radioMedium.isChecked = true
                else -> binding.radioHigh.isChecked = true
            }

            binding.txtTitle.setText(it.title)
            binding.txtNotes.setText(it.notes)
        })
    }

    override fun onRadioClick(v: View, priority: Int, obj: Todo) {
        obj.priority = priority
    }

    override fun onTodoSaveChangesClick(v: View, obj: Todo) {
        viewModel.update(obj.title, obj.notes, obj.priority, obj.uuid)
        Toast.makeText(v.context, "Todo Updated", Toast.LENGTH_SHORT).show()
    }

}