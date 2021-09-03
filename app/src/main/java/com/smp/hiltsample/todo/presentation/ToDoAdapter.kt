package com.smp.hiltsample.todo.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smp.hiltsample.databinding.ToDoListItemBinding
import com.smp.hiltsample.todo.domain.ToDo

class ToDoAdapter(
    private val onDeleteToDo: (Long) -> Unit
): RecyclerView.Adapter<ToDoViewHolder>() {

    private val toDoList = mutableListOf<ToDo>()

    @SuppressLint("NotifyDataSetChanged") // Just for testing
    fun swapData(toDoList: List<ToDo>) {
        this.toDoList.clear()
        this.toDoList.addAll(toDoList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ToDoListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding, onDeleteToDo)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    override fun getItemCount(): Int = toDoList.size
}

class ToDoViewHolder(
    private val binding: ToDoListItemBinding,
    private val onDeleteToDo: (Long) -> Unit
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.toDoDeleteButton.setOnClickListener {
            boundToDo?.let { onDeleteToDo(it.id) }
        }
    }

    private var boundToDo: ToDo? = null

    fun bind(toDo: ToDo) {
        boundToDo = toDo
        binding.toDoContentView.text = toDo.contents
    }
}