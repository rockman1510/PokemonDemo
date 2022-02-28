package com.pokemondemo.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

data class CoroutinesDispatcherProvider(
    val Main: CoroutineDispatcher,
    val Default: CoroutineDispatcher,
    val IO: CoroutineDispatcher
) {

    @Inject
    constructor() : this(Dispatchers.Main, Dispatchers.Default, Dispatchers.IO)
}
