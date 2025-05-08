package test.example.demo.room.application.service.command;


import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.room.application.dto.RoomCreateRequest;
import test.example.demo.room.application.dto.RoomUpdateRequest;

public interface RoomCommandService {
	
    Result createRoom(RoomCreateRequest request);

    Result updateRoom(Long roomId, RoomUpdateRequest request);

    Result deactivateRoom(Long roomId);

    Result addBedToRoom(Long roomId, BedCreateRequest request);

}
