package com.ywy.arch.ui.callback

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.*

/**
 *  Created by ywy on 2020/12/10
 * TODO：UnPeekLiveData 的存在是为了在 "重回二级页面" 的场景下，解决 "数据倒灌" 的问题。
 * 对 "数据倒灌" 的状况不理解的小伙伴，可参考《LiveData 数据倒灌 背景缘由全貌 独家解析》文章开头的解析
 * <p>
 * https://xiaozhuanlan.com/topic/6719328450
 * <p>
 * 本类参考了官方 SingleEventLive 的非入侵设计，
 * 以及小伙伴 Flywith24 在 wrapperLiveData 中通过 ViewModelStore 来唯一确定订阅者的思路，
 * <p>
 * TODO：在当前最新版中，我们透过对 ViewModelStore 的内存地址的遍历，
 * 来确保：
 * 1.一条消息能被多个观察者消费
 * 2.消息被所有观察者消费完毕后才开始阻止倒灌
 * 3.可以通过 clear 方法手动将消息从内存中移除
 * 4.让非入侵设计成为可能，遵循开闭原则
 * <p>
 * TODO：增加一层 ProtectedUnPeekLiveData，
 * 用于限制从 Activity/Fragment 篡改来自 "数据层" 的数据，数据层的数据务必通过 "唯一可信源" 来分发，
 * 如果这样说还不理解，详见：
 * https://xiaozhuanlan.com/topic/0168753249 和 https://xiaozhuanlan.com/topic/6719328450
 * <p>
 **/
open class ProtectedUnPeekLiveData<T> : LiveData<T>(){
    protected var mIsAllowNullValue = false
    private val mObservers = HashMap<Int, Boolean>()

    open fun observeInActivity(activity: AppCompatActivity, observer: Observer<in T?>) {
        val owner: LifecycleOwner = activity
        val storeId = System.identityHashCode(activity.viewModelStore)
        observe(storeId, owner, observer)
    }
    open fun observeInFragment(fragment: Fragment, observer: Observer<in T?>) {
        val owner = fragment.viewLifecycleOwner
        val storeId = System.identityHashCode(fragment.viewModelStore)
        observe(storeId, owner, observer)
    }

    open fun observe(storeId: Int,
                             owner: LifecycleOwner,
                             observer: Observer<in T?>) {
        mObservers[storeId]?:let {
            mObservers.put(storeId, true)
        }
        super.observe(owner, Observer { t: T? ->
            Log.e("requestTab","$storeId + ${mObservers[storeId]}")
            mObservers[storeId]?.let {
                if (!mObservers[storeId]!!) {
                    mObservers[storeId] = true
                    if (t != null || mIsAllowNullValue) {
                        observer.onChanged(t)
                    }
                }
            }
        })
    }

    /**
     * 重写的 setValue 方法，默认不接收 null
     * 可通过 Builder 配置允许接收
     * 可通过 Builder 配置消息延时清理的时间
     *
     *
     * override setValue, do not receive null by default
     * You can configure to allow receiving through Builder
     * And also, You can configure the delay time of message clearing through Builder
     *
     * @param value
     */
     override fun setValue(value: T?) {
        if (value != null || mIsAllowNullValue) {
            for (entry in mObservers.entries) {
                entry.setValue(false)
            }
            super.setValue(value)
        }
    }

     override fun postValue(value: T?) {
        if (value != null || mIsAllowNullValue) {
            for (entry in mObservers.entries) {
                entry.setValue(false)
            }
            super.postValue(value)
        }
    }

    protected open fun clear() {
        super.setValue(null)
    }

}