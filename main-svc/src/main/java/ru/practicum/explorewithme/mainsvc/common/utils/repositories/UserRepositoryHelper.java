package ru.practicum.explorewithme.mainsvc.common.utils.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.mainsvc.exception.AlreadyExistsException;
import ru.practicum.explorewithme.mainsvc.exception.EntityNotFoundException;
import ru.practicum.explorewithme.mainsvc.exception.dto.ErrorResponseDto;
import ru.practicum.explorewithme.mainsvc.user.entity.User;
import ru.practicum.explorewithme.mainsvc.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRepositoryHelper implements RepositoryByIdHelper<User> {
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                ErrorResponseDto.builder()
                        .status("NOT_FOUND")
                        .reason("User not found")
                        .message("User with id = " + id + " not found.")
                        .timestamp(LocalDateTime.now())
                        .build()
        ));
    }

    @Override
    public void checkExistenceById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    ErrorResponseDto.builder()
                            .status("NOT_FOUND")
                            .reason("User not found")
                            .message("User with id = " + id + " not found.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    public void checkEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException(
                    ErrorResponseDto.builder()
                            .status("CONFLICT")
                            .reason("User email must be unique")
                            .message("User with email = " + email + " already exists.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}
