package com.smp.hiltsample.auth

import com.smp.hiltsample.auth.UserAuth.Authenticated
import com.smp.hiltsample.auth.UserAuth.Unauthenticated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthSession @Inject constructor() {

    private val _currentAuth = MutableStateFlow<UserAuth>(Unauthenticated)
    val currentAuth = _currentAuth.asStateFlow()

    fun login(userId: String, password: String) {
        // Always success for testing
        _currentAuth.tryEmit(
            Authenticated(
                id = userId,
                credential = "credential://mock"
            )
        )
    }
    fun logout() {
        // Always success for testing
        _currentAuth.tryEmit(Unauthenticated)
    }

    fun isLoggedIn(): Boolean {
        return currentAuth.value is Authenticated
    }
}