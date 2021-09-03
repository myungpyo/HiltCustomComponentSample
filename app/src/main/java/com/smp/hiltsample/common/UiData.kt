package com.smp.hiltsample.common

sealed class UiData<out T> {

    object Loading: UiData<Nothing>()

    data class Success<T>(
        val data: T
    ): UiData<T>()

    data class Fail(
        val throwable: Throwable
    ): UiData<Nothing>()
}