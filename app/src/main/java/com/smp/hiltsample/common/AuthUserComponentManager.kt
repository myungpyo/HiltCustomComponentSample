package com.smp.hiltsample.common

import com.smp.hiltsample.auth.UserAuth
import com.smp.hiltsample.auth.UserAuthSession
import dagger.hilt.internal.GeneratedComponentManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AuthUserComponentManager @Inject constructor(
    applicationScope: ApplicationScope,
    private val userAuthSession: UserAuthSession,
    private val userComponentProvider: Provider<AuthUserComponent.Builder>
): GeneratedComponentManager<AuthUserComponent> {

    private val _versionState = MutableStateFlow(ComponentVersion.next())
    val versionState = _versionState.asStateFlow()

    var authUserComponent: AuthUserComponent = userComponentProvider.get().build()

    private var lastUserAuth: UserAuth = userAuthSession.currentAuth.value
    init {
        applicationScope.launch {
            userAuthSession.currentAuth.collect { userAuth ->
                if (lastUserAuth == userAuth) return@collect
                lastUserAuth = userAuth
                rebuildComponent()
            }
        }
    }

    suspend fun rebuildComponent() {
        authUserComponent = userComponentProvider.get().build()
        _versionState.emit(ComponentVersion.next())
    }

    fun getCurrentVersion(): ComponentVersion {
        return versionState.value
    }

    override fun generatedComponent(): AuthUserComponent = authUserComponent
}

data class ComponentVersion internal constructor(
    private val version: Int = versionSeq.incrementAndGet()
) {
    companion object {
        private val versionSeq = AtomicInteger(0)

        fun next(): ComponentVersion = ComponentVersion()
    }
}