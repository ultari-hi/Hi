package com.hi.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccommodationNotFoundException extends HiBusinessException {

    public AccommodationNotFoundException(String message) {
        super(message);
    }
}
