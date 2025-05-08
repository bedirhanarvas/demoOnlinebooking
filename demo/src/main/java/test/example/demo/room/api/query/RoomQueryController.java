package test.example.demo.room.api.query;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.room.application.dto.RoomDto;
import test.example.demo.room.application.service.query.RoomQueryService;
import test.example.demo.room.domain.enums.RoomType;

@RestController
@RequestMapping("/api/rooms/queries")
@RequiredArgsConstructor
public class RoomQueryController {

    private final RoomQueryService roomQueryService;

    @GetMapping("/{roomId}")
    public ResponseEntity<DataResult<RoomDto>> getRoomById(@PathVariable Long roomId) {
        DataResult<RoomDto> result = roomQueryService.getRoomById(roomId);
        return buildResponse(result);
    }

    @GetMapping("/roomNumber/{roomNumber}")
    public ResponseEntity<DataResult<RoomDto>> getRoomByRoomNumber(@PathVariable String roomNumber) {
        DataResult<RoomDto> result = roomQueryService.getRoomByRoomNumber(roomNumber);
        return buildResponse(result);
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<DataResult<List<RoomDto>>> getRoomsByType(@PathVariable RoomType roomType) {
        DataResult<List<RoomDto>> result = roomQueryService.getRoomsByType(roomType);
        return buildResponse(result);
    }

    private <T> ResponseEntity<DataResult<T>> buildResponse(DataResult<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
