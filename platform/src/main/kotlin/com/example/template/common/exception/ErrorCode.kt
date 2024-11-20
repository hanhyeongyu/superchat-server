package com.example.template.common.exception

enum class ErrorCode(
    val errorMessage: String,
    val errorCode: Int
) {
    // Common
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", -1),
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다.", -2),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다.",-3),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다.", -4),

    // Security
    TOKEN_NOT_FOUND("토큰이 필요합니다.",  -401),
    INVALID_ACCESS_TOKEN("잘못된 엑세스 토큰입니다.",  -401),
    INVALID_REFRESH_TOKEN("잘못된 리프레시 토큰입니다.",  -401),
    ACCESS_DENIED("접근 권한이 없습니다.", -403),
    INVALID_PASSWORD("잘못된 패스워드 입니다.",  -401),
}