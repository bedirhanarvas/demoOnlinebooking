package test.example.demo.room.application.service.query.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.core.utilities.results.SuccessDataResult;
import test.example.demo.room.application.dto.RoomDto;
import test.example.demo.room.application.mapper.RoomMapper;
import test.example.demo.room.application.service.query.RoomQueryService;
import test.example.demo.room.dataaccess.repository.RoomRepository;
import test.example.demo.room.domain.entity.Room;
import test.example.demo.room.domain.enums.RoomType;

@Service
@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService{

	private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    
	@Override
	public DataResult<RoomDto> getRoomById(Long roomId) {
		Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Oda bulunamadı: ID = " + roomId));

        RoomDto roomDto = roomMapper.toDto(room);
        return new SuccessDataResult<>(roomDto, "Oda başarıyla getirildi.");
	}
	
	@Override
	public DataResult<RoomDto> getRoomByRoomNumber(String roomNumber) {
		
		Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Oda bulunamadı: Oda No = " + roomNumber));

        RoomDto roomDto = roomMapper.toDto(room);
        return new SuccessDataResult<>(roomDto, "Oda başarıyla getirildi.");
	}
	@Override
	public DataResult<List<RoomDto>> getRoomsByType(RoomType roomType) {
		List<Room> rooms = roomRepository.findByRoomType(roomType);

        List<RoomDto> roomDtos = rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());

        return new SuccessDataResult<>(roomDtos, "Oda listesi başarıyla getirildi.");
	}
	
}
