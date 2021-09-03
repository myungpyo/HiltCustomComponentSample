package com.smp.hiltsample.common

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyValueStore @Inject constructor() {
    private val mutex = Mutex(false)
    private val map = mutableMapOf<String, Any>()

    suspend fun put(key: String, value: Any?) {
        mutex.withLock {
            value?.let {
                map[key] = it
            } ?: map.remove(key)
        }
    }

    suspend fun get(key: String): Any? {
        mutex.withLock {
            return map[key]
        }
    }

    suspend fun <T: Any> getOrPutIfAbsent(key: String, value: T): T {
        mutex.withLock {
            val result = map[key] ?: value
            map[key] = result
            return result as T
        }
    }
}