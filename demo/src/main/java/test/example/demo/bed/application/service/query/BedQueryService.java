package test.example.demo.bed.application.service.query;

import java.time.LocalDateTime;
import java.util.List;

import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.room.domain.enums.RoomType;

public interface BedQueryService {
	
	DataResult<List<Bed>> getAvailableBeds(RoomType roomType, LocalDateTime start, LocalDateTime end);
	
    DataResult<BedDto> getBedById(Long bedId);

    DataResult<BedDto> getBedByBedNumber(String bedNumber);

    DataResult<List<BedDto>> getBedsByRoom(Long roomId);

    DataResult<List<BedDto>> getAvailableBeds();

}
