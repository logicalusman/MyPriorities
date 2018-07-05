package com.mypriorities.domain

sealed class ErrorType(val errorBody: String? = null) {

    class NetworkError : ErrorType()
    class TimeoutError : ErrorType()
    class UnknownError(errorBody: String?) : ErrorType(errorBody)

}