package test.example.demo.bed.api.query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.bed.application.service.query.BedQueryService;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.room.domain.enums.RoomType;

@RestController
@RequestMapping("/api/beds/queries")
@RequiredArgsConstructor
public class BedQueryController {

    private final BedQueryService bedQueryService;

    @GetMapping("/available")
    public ResponseEntity<DataResult<List<Bed>>> getAvailableBeds(
            @RequestParam RoomType roomType,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end) {

        DataResult<List<Bed>> result = bedQueryService.getAvailableBeds(roomType, start, end);
        return buildResponse(result);
    }

    @GetMapping("/{bedId}")
    public ResponseEntity<DataResult<BedDto>> getBedById(@PathVariable Long bedId) {
        DataResult<BedDto> result = bedQueryService.getBedById(bedId);
        return buildResponse(result);
    }

    // Yatak numarasına göre yatak sorgula
    @GetMapping("/bed-number/{bedNumber}")
    public ResponseEntity<DataResult<BedDto>> getBedByBedNumber(@PathVariable String bedNumber) {
        DataResult<BedDto> result = bedQueryService.getBedByBedNumber(bedNumber);
        return buildResponse(result);
    }

    // Odaya ait yatakları getir
    @GetMapping("/room/{roomId}")
    public ResponseEntity<DataResult<List<BedDto>>> getBedsByRoom(@PathVariable Long roomId) {
        DataResult<List<BedDto>> result = bedQueryService.getBedsByRoom(roomId);
        return buildResponse(result);
    }

    // Müsait tüm yatakları getir
    @GetMapping("/available/all")
    public ResponseEntity<DataResult<List<BedDto>>> getAvailableBeds() {
        DataResult<List<BedDto>> result = bedQueryService.getAvailableBeds();
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

