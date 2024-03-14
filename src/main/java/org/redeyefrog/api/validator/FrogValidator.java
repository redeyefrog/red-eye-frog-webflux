package org.redeyefrog.api.validator;

import org.redeyefrog.enums.StatusCode;
import org.redeyefrog.exception.FrogRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FrogValidator {

    @Autowired
    javax.validation.Validator validator;

//    @Autowired
//    org.springframework.validation.Validator validator;

    public <T> T validate(T value) {
        List<String> errorMessages = validator.validate(value)
                                              .stream()
                                              .map(ConstraintViolation::getMessage)
                                              .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errorMessages)) {
            String message = String.join(",", errorMessages);
            throw new FrogRuntimeException(StatusCode.VALIDATE_ERROR, message, new IllegalArgumentException(message));
        }
//        BindingResult bindingResult = new BeanPropertyBindingResult(value, value.getClass().getName());
//        validator.validate(value, bindingResult);
//        if (bindingResult.hasErrors()) {
//            List<String> messages = bindingResult.getAllErrors()
//                                                 .stream()
//                                                 .map(ObjectError::getDefaultMessage)
//                                                 .collect(Collectors.toList());
//            String message = String.join(",", messages);
//            throw new FrogRuntimeException(StatusCode.VALIDATE_ERROR, message, new BindException(bindingResult));
//        }
        return value;
    }

}
