package test.example.demo.room.application.service.query;

import java.util.List;

import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.room.application.dto.RoomDto;
import test.example.demo.room.domain.enums.RoomType;

public interface RoomQueryService {
	
	DataResult<RoomDto> getRoomById(Long roomId);

	DataResult<RoomDto> getRoomByRoomNumber(String roomNumber);
	
	DataResult<List<RoomDto>> getRoomsByType(RoomType roomType);
}
