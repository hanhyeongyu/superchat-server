package com.example.template.common.exception

import com.example.template.common.exception.BaseException
import com.example.template.common.exception.ErrorCode
import com.example.template.common.exception.ErrorCode.COMMON_INVALID_PARAMETER

class InvalidParamException : BaseException {
    constructor() : super(COMMON_INVALID_PARAMETER)

    constructor(errorCode: ErrorCode) : super(errorCode)

    constructor(errorMsg: String) : super(errorMsg, COMMON_INVALID_PARAMETER)

    constructor(message: String, errorCode: ErrorCode) : super(message, errorCode)
}
