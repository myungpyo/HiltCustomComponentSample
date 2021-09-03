package com.smp.hiltsample.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.smp.hiltsample.auth.UserAuthSession
import com.smp.hiltsample.common.Utils
import com.smp.hiltsample.databinding.LoginDialogFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginDialogFragment: DialogFragment() {

    @Inject
    lateinit var userAuthSession: UserAuthSession

    private var _binding: LoginDialogFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateLoginButtonState()
        binding.userIdView.addTextChangedListener {
            updateLoginButtonState()
        }
        binding.userPwView.addTextChangedListener {
            updateLoginButtonState()
        }

        binding.loginButton.setOnClickListener {
            val userId = binding.userIdView.text.toString()
            val userPw = binding.userPwView.text.toString()
            userAuthSession.login(userId, userPw)
            dismissAllowingStateLoss()
        }

        Utils.showSoftKeyboard(binding.userIdView)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    private fun updateLoginButtonState() {
        binding.loginButton.isEnabled = binding.userIdView.text.isBlank().not() && binding.userPwView.text.isBlank().not()
    }

    companion object {
        fun show(fm: FragmentManager) {
            LoginDialogFragment().show(fm, null)
        }
    }
}