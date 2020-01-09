package com.example.lenovo.rxjavathrottle

class RxJavaThrottleManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RxJavaThrottleManager()
        }
    }

    fun throttleFirst(period: Int) {

    }

    fun throttleLast(period: Int) {

    }
}
