package com.ywy.arch.data

import com.jzy.ksmart.architecture.domain.manager.NetState

/**
 *  Created by ywy on 2020/11/12
 *
 **/
class DataResult<T>(collect : (t: T?, netState: NetState?, message: String?)->Unit) {
    private var mT: T? = null
    private var mNetState: NetState? = null
    private var mCollect = collect
    fun getResult(): T? {
        return mT
    }

    fun getNetState(): NetState? {
        return mNetState
    }

    fun setResult(t: T?, netState: NetState?, message: String?) {
        if (netState == null) {
            throw NullPointerException("Need to instantiate the NetState first ...")
        }
        mT = t
        mNetState = netState
        mCollect(t, netState, message)
    }


    fun onObserve(collect : (t: T?, netState: NetState?, message: String?)->Unit) {
            mCollect = collect
    }

    interface Result<T> {
        fun collect(t: T, netState: NetState?, message: String?)
    }
}
