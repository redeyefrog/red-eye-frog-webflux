package org.redeyefrog.exception;

import lombok.Getter;
import org.redeyefrog.enums.StatusCode;

@Getter
public class FrogRuntimeException extends RuntimeException {

    private StatusCode statusCode;

    public FrogRuntimeException(StatusCode statusCode) {
        this(statusCode, null);
    }

    public FrogRuntimeException(StatusCode statusCode, Throwable cause) {
        this(statusCode, statusCode.getDesc(), cause);
    }

    public FrogRuntimeException(StatusCode statusCode, String message, Throwable cause) {
        this(message, cause);
        this.statusCode = statusCode;
    }

    public FrogRuntimeException(String message, Throwable cause) {
        super(message, cause, true, false);
    }

}
