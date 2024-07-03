package ru.practicum.explorewithme.mainsvc.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.mainsvc.common.requests.PaginationRequest;
import ru.practicum.explorewithme.mainsvc.event.dto.*;
import ru.practicum.explorewithme.mainsvc.event.service.EventService;
import ru.practicum.explorewithme.mainsvc.request.dto.RequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {
    private final EventService eventService;
    private final EventValidator eventValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto postEvent(@RequestBody @Valid EventCreationDto eventDto, @PathVariable long userId) {
        log.info("POST /users/{}/events. Body : {}", userId, eventDto);
        eventValidator.validateEventCreationDto(eventDto);
        return eventService.addEvent(eventDto, userId);
    }

    @PatchMapping("/{eventId}")
    public EventDto patchEvent(@RequestBody @Valid EventUserUpdateDto eventDto,
                               @PathVariable long userId, @PathVariable long eventId) {
        log.info("PATCH /users/{}/events/{}. Body : {}", userId, eventId, eventDto);
        eventValidator.validateEventUserUpdateDto(eventDto);
        return eventService.updateEventByUser(eventId, eventDto, userId);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable long userId, @PathVariable long eventId) {
        log.info("GET /users/{}/events/{}", userId, eventId);
        return eventService.getEventById(eventId, userId);
    }

    @GetMapping
    public List<EventShortDto> getEventsByUser(@PathVariable long userId,
                                               @ModelAttribute @Validated PaginationRequest request) {
        log.info("GET /users/{}/events?from={}&size={}", userId, request.getFrom(), request.getSize());
        return eventService.getEventsByUser(userId, request);
    }

    @PatchMapping("/{eventId}/requests")
    public RequestStatusUpdateResultDto patchEventRequest(@RequestBody @Valid RequestStatusUpdateRequestDto request,
                                                          @PathVariable long userId, @PathVariable long eventId) {
        log.info("PATCH /users/{}/events/{}/requests. Body : {}", userId, eventId, request);
        eventValidator.validateEventRequestStatusUpdateRequest(request);
        return eventService.updateEventRequestsByUser(eventId, request, userId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        log.info("GET /users/{}/events/{}/requests", userId, eventId);
        return eventService.getEventRequestsByUser(eventId, userId);
    }
}
