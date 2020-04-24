package com.p3achb0t.api

import com.p3achb0t.api.interfaces.Client
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class ClientContext(val ctx: Context): AbstractCoroutineContextElement(ClientContext)
{

	val client = ctx.client

	/**
	 * The [Client] instance that is defined on runtime of the current tab.
	 */

	companion object : CoroutineContext.Key<ClientContext>
}

suspend fun client(): Client
{
	return coroutineContext[ClientContext]?.client ?: error("ClientContext not found.")
}

suspend fun ctx(): Context
{
	return coroutineContext[ClientContext]?.ctx ?: error("ClientContext not found.")
}