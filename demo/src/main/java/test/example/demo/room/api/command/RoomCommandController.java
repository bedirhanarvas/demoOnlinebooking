package test.example.demo.room.api.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.room.application.dto.RoomCreateRequest;
import test.example.demo.room.application.dto.RoomUpdateRequest;
import test.example.demo.room.application.service.command.RoomCommandService;

@RestController
@RequestMapping("/api/rooms/commands")
@RequiredArgsConstructor
public class RoomCommandController {

    private final RoomCommandService roomCommandService;

    @PostMapping
    public ResponseEntity<Result> createRoom(@RequestBody @Valid RoomCreateRequest request) {
        Result result = roomCommandService.createRoom(request);
        return buildResponse(result);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Result> updateRoom(@PathVariable Long roomId, 
                                             @RequestBody @Valid RoomUpdateRequest request) {
        Result result = roomCommandService.updateRoom(roomId, request);
        return buildResponse(result);
    }

    @PutMapping("/{roomId}/deactivate")
    public ResponseEntity<Result> deactivateRoom(@PathVariable Long roomId) {
        Result result = roomCommandService.deactivateRoom(roomId);
        return buildResponse(result);
    }

    @PostMapping("/{roomId}/beds")
    public ResponseEntity<Result> addBedToRoom(@PathVariable Long roomId, 
                                               @RequestBody @Valid BedCreateRequest request) {
        Result result = roomCommandService.addBedToRoom(roomId, request);
        return buildResponse(result);
    }

    private ResponseEntity<Result> buildResponse(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

