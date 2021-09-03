package com.smp.hiltsample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.smp.hiltsample.R
import com.smp.hiltsample.auth.UserAuth
import com.smp.hiltsample.auth.UserAuthSession
import com.smp.hiltsample.common.AuthUserComponentManager
import com.smp.hiltsample.common.ComponentVersion
import com.smp.hiltsample.databinding.MainActivityBinding
import com.smp.hiltsample.todo.presentation.ToDoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userAuthSession: UserAuthSession

    @Inject
    lateinit var authUserComponentManager: AuthUserComponentManager

    lateinit var lastAuthUserComponentVersion: ComponentVersion

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastAuthUserComponentVersion = authUserComponentManager.versionState.value
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeUserAuth()
        savedInstanceState ?: organizeFragments()
        observeUserAuthComponentVersion()
    }

    private fun organizeFragments() {
        if (userAuthSession.isLoggedIn()) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, ToDoFragment(), ToDoFragment.TAG)
                .commitAllowingStateLoss()
        } else {
            val toDoFragment = supportFragmentManager.findFragmentByTag(ToDoFragment.TAG) ?: return
            supportFragmentManager.beginTransaction()
                .remove(toDoFragment)
                .commitAllowingStateLoss()
        }
    }

    private fun observeUserAuth() {
        lifecycleScope.launchWhenStarted {
            userAuthSession.currentAuth.collect { userAuth ->
                when(userAuth) {
                    is UserAuth.Authenticated -> {
                        binding.welcomeMessageView.text = "Welcome, ${userAuth.id}"
                        binding.loginOutButton.text = "Logout"
                        binding.loginOutButton.setOnClickListener {
                            userAuthSession.logout()
                        }
                    }
                    is UserAuth.Unauthenticated -> {
                        binding.welcomeMessageView.text = "Plz, login ->"
                        binding.loginOutButton.text = "LogIn"
                        binding.loginOutButton.setOnClickListener {
                            LoginDialogFragment.show(supportFragmentManager)
                        }
                    }
                }
            }
        }
    }

    private fun observeUserAuthComponentVersion() {
        lifecycleScope.launchWhenStarted {
            authUserComponentManager.versionState.collect { componentVersion ->
                if (lastAuthUserComponentVersion == componentVersion) return@collect
                lastAuthUserComponentVersion = componentVersion
                organizeFragments()
            }
        }
    }
}