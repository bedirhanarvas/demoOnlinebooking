package test.example.demo.bed.application.service.query.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.bed.application.mapper.BedMapper;
import test.example.demo.bed.application.service.query.BedQueryService;
import test.example.demo.bed.dataaccess.repository.BedRepository;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.core.utilities.results.ErrorDataResult;
import test.example.demo.core.utilities.results.SuccessDataResult;
import test.example.demo.room.domain.enums.RoomType;

@Service
@RequiredArgsConstructor
public class BedQueryServiceImpl implements BedQueryService {

	private final BedRepository bedRepository;
	private final BedMapper bedMapper;

	@Override
	public DataResult<List<Bed>> getAvailableBeds(RoomType roomType, LocalDateTime start, LocalDateTime end) {
		return new SuccessDataResult<List<Bed>>(bedRepository.findAvailableBeds(roomType, start, end),
				"Müsait yataklar getirildi.");
	}

	@Override
	public DataResult<BedDto> getBedById(Long bedId) {
		Optional<Bed> bedOpt = bedRepository.findById(bedId);
		if (bedOpt.isEmpty()) {
			return new ErrorDataResult<>(null, "Yatak bulunamadı.");
		}
		return new SuccessDataResult<>(bedMapper.toDto(bedOpt.get()), "Yatak başarıyla getirildi.");
	}

	@Override
	public DataResult<BedDto> getBedByBedNumber(String bedNumber) {
		Optional<Bed> bedOpt = bedRepository.findByBedNumber(bedNumber);
		if (bedOpt.isEmpty()) {
			return new ErrorDataResult<>(null, "Yatak numarası ile yatak bulunamadı.");
		}
		return new SuccessDataResult<>(bedMapper.toDto(bedOpt.get()), "Yatak başarıyla getirildi.");
	}

	@Override
	public DataResult<List<BedDto>> getBedsByRoom(Long roomId) {
		List<Bed> beds = bedRepository.findByRoomId(roomId);
		if (beds.isEmpty()) {
			return new ErrorDataResult<>(null, "Bu odaya ait yatak bulunamadı.");
		}
		List<BedDto> bedDtos = beds.stream().map(bedMapper::toDto).collect(Collectors.toList());
		return new SuccessDataResult<List<BedDto>>(bedDtos, "Yataklar başarıyla getirildi.");
	}

	@Override
	public DataResult<List<BedDto>> getAvailableBeds() {
		List<Bed> availableBeds = bedRepository.findByIsAvailable(true);
		if (availableBeds.isEmpty()) {
			return new ErrorDataResult<>(null, "Boş yatak bulunamadı.");
		}
		List<BedDto> bedDtos = availableBeds.stream().map(bedMapper::toDto).collect(Collectors.toList());
		return new SuccessDataResult<List<BedDto>>(bedDtos, "Yataklar başarıyla getirildi.");
	}

}
