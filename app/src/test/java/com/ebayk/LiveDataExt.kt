package com.ebayk

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

// It's a modified version of the method that Google nudges us to use in the code lab
// https://developer.android.com/codelabs/advanced-android-kotlin-training-testing-basics#8
fun <T> LiveData<T>.getOrAwaitValues(
    numberOfExpectedValues: Int = 1,
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): List<T> {
    val data = mutableListOf<T>()
    val latch = CountDownLatch(numberOfExpectedValues)
    val observer = object : Observer<T> {
        override fun onChanged(obj: T?) {
            obj ?: return
            data.add(obj)
            latch.countDown()
            if (data.size == numberOfExpectedValues) {
                this@getOrAwaitValues.removeObserver(this)
            }
        }
    }
    this.observeForever(observer)

    try {
        afterObserve()

        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    return data
}