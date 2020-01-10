package com.example.lenovo.throttle

import android.os.Handler
import android.os.Message
import kotlin.concurrent.thread
/**
 * @author Adam
 * @date 2020/1/10
 * GitHub: https://github.com/adam0806
 * jianshu: https://www.jianshu.com/u/83868141c938
 * Email: adam0806@gmail
 */
class Throttle<T>(builder:Builder<T>){
    companion object {
        const val DEFAULT_PERIOD = 200L;

        const val THROTTLE_FIRST = 0;
        const val THROTTLE_LAST = 1;

        const val MESSAGE_ONSUBSCRIBE = 0;

        var mMutableMap:MutableMap<Int, Thread> = mutableMapOf();

    }
    interface Observable{
        fun onSubscribe()
    }
    class Builder<T>(t:T){
        var mPeriod = DEFAULT_PERIOD;
        var mT:T ?= null;
        var mFlag = THROTTLE_FIRST;
        var mObservable:Observable? = null;
        val mHandler : Handler = object : Handler(){
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                when(msg?.what){
                    MESSAGE_ONSUBSCRIBE->
                        mObservable?.onSubscribe()
                }
            }
        }
        init {
            mT = t;
        }
        fun subscribe(observable: Observable): Builder<T> {
            mObservable = observable;
            return this;
        }
        fun throttle(): Builder<T>{
            if(!mMutableMap.contains(mT?.hashCode())){
                var thread:Thread = thread{
                    if(mFlag == THROTTLE_FIRST){
                        mHandler.sendEmptyMessage(MESSAGE_ONSUBSCRIBE)
                        Thread.sleep(mPeriod)
                    }else if(mFlag == THROTTLE_LAST) {
                        Thread.sleep(mPeriod)
                        mHandler.sendEmptyMessage(MESSAGE_ONSUBSCRIBE)
                    }
                    mMutableMap.remove(mT?.hashCode())
                }
                mMutableMap.put(mT?.hashCode()!!,thread);
            }
            return this;
        }
        fun throttleFirst(period: Long): Builder<T> {
            mPeriod = period
            mFlag = THROTTLE_FIRST
            return throttle()
        }
        fun throttleLast(period: Long): Builder<T> {
            mPeriod = period
            mFlag = THROTTLE_LAST
            return throttle()
        }
    }
}
