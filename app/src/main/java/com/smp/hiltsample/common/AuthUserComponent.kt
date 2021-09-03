package com.smp.hiltsample.common

import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent

@AuthUserScope
@DefineComponent(parent = SingletonComponent::class)
interface AuthUserComponent {

    @DefineComponent.Builder
    interface Builder {
        fun build(): AuthUserComponent
    }
}