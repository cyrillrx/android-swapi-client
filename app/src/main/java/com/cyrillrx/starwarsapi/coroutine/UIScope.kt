package com.cyrillrx.starwarsapi.coroutine

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

/**
 * @author Cyril Leroux
 *         Created on 05/12/2019.
 */
class UIScope : CoroutineScope, LifecycleObserver {

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
        coroutineContext.cancelChildren()
    }
}