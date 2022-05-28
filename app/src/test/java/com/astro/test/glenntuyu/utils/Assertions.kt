package com.astro.test.glenntuyu.utils

/**
 * Created by glenntuyu on 28/05/2022.
 */
internal infix fun Any?.shouldBe(expectedValue: Any?) {
    if (this != expectedValue) {
        throw AssertionError("$this should be $expectedValue")
    }
}

internal inline fun <reified T> Any?.shouldBeInstanceOf() {
    if (this !is T) {
        val actualClassName = if (this == null) "null" else this::class.simpleName
        val expectedClassName = T::class.simpleName

        throw AssertionError("$actualClassName should be instance of $expectedClassName")
    }
}