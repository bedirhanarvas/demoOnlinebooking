package test.example.demo.room.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.bed.application.mapper.BedMapper;
import test.example.demo.room.application.dto.RoomCreateRequest;
import test.example.demo.room.application.dto.RoomDto;
import test.example.demo.room.application.dto.RoomUpdateRequest;
import test.example.demo.room.domain.entity.Room;

@Component
@RequiredArgsConstructor
public class RoomMapper {
	
	private final BedMapper bedMapper;
	
	public Room toEntity(RoomCreateRequest request) {
        return Room.builder()
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .isActive(true) 
                .build();
    }

    public void updateEntityFromDto(Room room, RoomUpdateRequest request) {
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        // isActive dışarıdan kontrol ettirilmeyecekse güncellenmez
    }

    public RoomDto toDto(Room room) {
    	
    	 List<BedDto> bedDtos = room.getBeds().stream()
                 .map(bedMapper::toDto)
                 .collect(Collectors.toList());

         return RoomDto.builder()
                 .id(room.getId())
                 .roomNumber(room.getRoomNumber())
                 .roomType(room.getRoomType())
                 .isActive(room.isActive())
                 .beds(bedDtos)
                 .build();
    }

}
