package ru.practicum.explorewithme.mainsvc.exception;

import ru.practicum.explorewithme.mainsvc.exception.dto.ErrorResponseDto;

public class IllegalPageableArgumentsException extends EwmBaseRuntimeException {
    public IllegalPageableArgumentsException(ErrorResponseDto response) {
        super(response);
    }
}
