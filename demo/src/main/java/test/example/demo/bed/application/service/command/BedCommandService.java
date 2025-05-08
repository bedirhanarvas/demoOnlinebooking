package test.example.demo.bed.application.service.command;


import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.core.utilities.results.Result;

public interface BedCommandService {

	    Result createBed(BedCreateRequest request);

	    Result updateBedStatus(Long bedId, boolean isAvailable);

	    Result deleteBed(Long bedId);

	    Result assignBedToRoom(Long roomId, BedCreateRequest request);

	    Result unassignBedFromRoom(Long bedId);
	
}
