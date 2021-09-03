package com.smp.hiltsample.common

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    return viewModels({ requireParentFragment() }, factoryProducer)
}