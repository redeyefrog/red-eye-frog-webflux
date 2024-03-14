package org.redeyefrog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    SUCCESS("0000", "Success"),

    VALIDATE_ERROR("0001", "Validate error"),

    DATA_ALREADY_EXIST("0002", "Data already exist"),

    DATA_NOT_EXIST("0003", "Data not exist"),

    FAIL("4000", "Fail"),

    CALL_TELEGRAM_ERROR("5000", "Call telegram fail"),

    SYSTEM_ERROR("9999", "System Error");

    private String code;

    private String desc;

}
