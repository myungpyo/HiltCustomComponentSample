package com.smp.hiltsample.todo.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.smp.hiltsample.auth.UnAuthorizedException
import com.smp.hiltsample.common.UiData
import com.smp.hiltsample.databinding.ToDoFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ToDoFragment : Fragment() {

    private var _binding: ToDoFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by viewModels()
    private val toDoAdapter = ToDoAdapter { id ->
        viewModel.deleteToDo(id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ToDoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoSwipeRefreshView.setOnRefreshListener {
            viewModel.loadToDoList()
        }

        binding.addToDoButton.setOnClickListener {
            AddToDoDialogFragment.show(childFragmentManager)
        }

        binding.todoListView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = toDoAdapter
        }

        lifecycleScope.launchWhenStarted {
            viewModel.toDoList.collect { toDoUiData ->
                when(toDoUiData) {
                    is UiData.Loading -> {
                        binding.todoSwipeRefreshView.isRefreshing = true
                    }
                    is UiData.Success -> {
                        binding.todoSwipeRefreshView.isRefreshing = false
                        toDoAdapter.swapData(toDoUiData.data)
                    }
                    is UiData.Fail -> {
                        binding.todoSwipeRefreshView.isRefreshing = false
                        showErrorMessage(toDoUiData.throwable)
                    }
                }
            }
        }

        viewModel.loadToDoList()
    }

    private fun showErrorMessage(throwable: Throwable) {
        when(throwable) {
            is UnAuthorizedException -> {
                Toast.makeText(requireContext(), "Access denied. Login required", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    companion object {
        const val TAG = "ToDoFragment"
    }

}