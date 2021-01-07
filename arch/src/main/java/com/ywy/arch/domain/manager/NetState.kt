package com.ywy.arch.domain.manager

/**
 *  Created by ywy on 2020/11/12
 *
 **/
class NetState {
    private var responseCode: String? = null
    private var success = true

    constructor(responseCode: String?, success: Boolean) {
        this.responseCode = responseCode
        this.success = success
    }

    constructor() {}

    fun getResponseCode(): String? {
        return responseCode
    }

    fun setResponseCode(responseCode: String?) {
        this.responseCode = responseCode
    }

    fun isSuccess(): Boolean {
        return success
    }

    fun setSuccess(success: Boolean) {
        this.success = success
    }
}
