package com.raven.home.utils

class Resource<T> (
    val status: Status,
    val data : T? = null,
    val message: String? = null,
    val code: Int? = null
) {
        companion object {
            fun <T> success(data: T?) = Resource(Status.SUCCESS, data)
            fun <T> error(code: Int?= null, msg: String? = null, data:T? =null) = Resource(Status.ERROR,code = code, message = msg, data = data)
            fun <T> loading(msg: Int? = null) = Resource<T>(Status.LOADING)
        }
    }

enum class Status {
    ERROR,
    SUCCESS,
    LOADING
}


