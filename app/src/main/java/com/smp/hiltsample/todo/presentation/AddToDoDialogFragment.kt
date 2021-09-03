package com.smp.hiltsample.todo.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.smp.hiltsample.auth.UserAuthSession
import com.smp.hiltsample.common.Utils
import com.smp.hiltsample.common.parentViewModels
import com.smp.hiltsample.databinding.AddToDoDialogFragmentBinding
import com.smp.hiltsample.databinding.LoginDialogFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddToDoDialogFragment: DialogFragment() {

    private val viewModel: ToDoViewModel by parentViewModels()

    private var _binding: AddToDoDialogFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddToDoDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.isEnabled = false
        binding.toDoView.addTextChangedListener {
            binding.confirmButton.isEnabled = binding.toDoView.text.isBlank().not()
        }

        binding.confirmButton.setOnClickListener {
            val content = binding.toDoView.text.toString()
            viewModel.addToDo(content)
            dismissAllowingStateLoss()
        }

        binding.cancelButton.setOnClickListener {
            dismissAllowingStateLoss()
        }

        Utils.showSoftKeyboard(binding.toDoView)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        fun show(fm: FragmentManager) {
            AddToDoDialogFragment().show(fm, null)
        }
    }
}

