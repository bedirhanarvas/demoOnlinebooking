package test.example.demo.bed.api.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.bed.application.service.command.BedCommandService;
import test.example.demo.core.utilities.results.Result;

@RestController
@RequestMapping("/api/beds/commands")
@RequiredArgsConstructor
public class BedCommandController {

    private final BedCommandService bedCommandService;

    @PostMapping
    public ResponseEntity<Result> createBed(@RequestBody BedCreateRequest request) {
        Result result = bedCommandService.createBed(request);
        return buildResponse(result);
    }

    @PutMapping("/{bedId}/status")
    public ResponseEntity<Result> updateBedStatus(@PathVariable Long bedId, @RequestParam boolean isAvailable) {
        Result result = bedCommandService.updateBedStatus(bedId, isAvailable);
        return buildResponse(result);
    }

    @DeleteMapping("/{bedId}")
    public ResponseEntity<Result> deleteBed(@PathVariable Long bedId) {
        Result result = bedCommandService.deleteBed(bedId);
        return buildResponse(result);
    }

    @PostMapping("/{roomId}/assign")
    public ResponseEntity<Result> assignBedToRoom(@PathVariable Long roomId, @RequestBody BedCreateRequest request) {
        Result result = bedCommandService.assignBedToRoom(roomId, request);
        return buildResponse(result);
    }

    @PutMapping("/{bedId}/unassign")
    public ResponseEntity<Result> unassignBedFromRoom(@PathVariable Long bedId) {
        Result result = bedCommandService.unassignBedFromRoom(bedId);
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

