package com.example.template.common.response

import com.example.template.common.exception.BaseException
import com.example.template.common.exception.ErrorCode



class ResponseException(
    val message: String? = null,
    val errorCode: Int? = null
){

    constructor(exception: BaseException): this(
        message = exception.message,
        errorCode = exception.errorCode?.errorCode
    )

    constructor(errorCode: ErrorCode): this(
        message = errorCode.errorMessage,
        errorCode = errorCode.errorCode
    )
}
