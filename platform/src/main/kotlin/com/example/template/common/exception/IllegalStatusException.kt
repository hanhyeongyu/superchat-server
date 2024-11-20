package com.example.template.common.exception

import com.example.template.common.exception.ErrorCode.COMMON_ILLEGAL_STATUS


class IllegalStatusException : BaseException {
    constructor() : super(COMMON_ILLEGAL_STATUS)

    constructor(message: String) : super(message, COMMON_ILLEGAL_STATUS)
}
