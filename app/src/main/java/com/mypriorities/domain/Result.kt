package com.mypriorities.domain

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class Result<T>(val data: T? = null, val error: Throwable? = null) {

    val success: Boolean = data != null
    var errorType: ErrorType? = null

    companion object {
        fun <T> fomData(data: T): Result<T> = Result(data, null)
        fun <T> fromError(error: Throwable): Result<T> {
            val r = Result<T>(null, error)
            when (error) {
                is HttpException -> r.errorType = ErrorType.UnknownError(error.response().errorBody().toString())
                is SocketTimeoutException -> r.errorType = ErrorType.TimeoutError()
                is IOException -> r.errorType = ErrorType.NetworkError()
                else -> r.errorType = ErrorType.UnknownError(error.message)
            }
            return r
        }
    }
}