package ru.practicum.explorewithme.mainsvc.location.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mainsvc.common.utils.exceptions.ByIdExceptionThrower;
import ru.practicum.explorewithme.mainsvc.exception.EntityNotFoundException;
import ru.practicum.explorewithme.mainsvc.exception.dto.ErrorResponseDto;
import ru.practicum.explorewithme.mainsvc.location.entity.Location;
import ru.practicum.explorewithme.mainsvc.location.entity.LocationPrimaryKey;
import ru.practicum.explorewithme.mainsvc.location.repository.LocationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationExceptionThrower implements ByIdExceptionThrower<Location, LocationPrimaryKey> {
    private final LocationRepository locationRepository;

    @Override
    public Location findById(LocationPrimaryKey id) {
        return locationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                ErrorResponseDto.builder()
                        .status(HttpStatus.NOT_FOUND.toString())
                        .reason("Location not found.")
                        .message("Location with id = " + id + " not found.")
                        .timestamp(LocalDateTime.now())
                        .build()
        ));
    }

    @Override
    public void checkExistenceById(LocationPrimaryKey id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    ErrorResponseDto.builder()
                            .status(HttpStatus.NOT_FOUND.toString())
                            .reason("Location not found.")
                            .message("Location with id = " + id + " not found.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}