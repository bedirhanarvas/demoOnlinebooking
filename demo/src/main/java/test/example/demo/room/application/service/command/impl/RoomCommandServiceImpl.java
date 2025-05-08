package test.example.demo.room.application.service.command.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.bed.dataaccess.repository.BedRepository;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.ErrorResult;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.core.utilities.results.SuccessResult;
import test.example.demo.room.application.dto.RoomCreateRequest;
import test.example.demo.room.application.dto.RoomUpdateRequest;
import test.example.demo.room.application.mapper.RoomMapper;
import test.example.demo.room.application.service.command.RoomCommandService;
import test.example.demo.room.dataaccess.repository.RoomRepository;
import test.example.demo.room.domain.entity.Room;
import test.example.demo.room.domain.enums.RoomType;

@Service
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {

	private final RoomRepository roomRepository;
	private final BedRepository bedRepository;
	private final RoomMapper roomMapper;

	@Override
	@Transactional
	public Result createRoom(RoomCreateRequest request) {

		if (roomRepository.existsByRoomNumber(request.getRoomNumber())) {
			return new ErrorResult("Bu oda numarası zaten kayıtlı.");
		}

		List<String> bedNumbers = request.getBedNumbers();

		if (request.getRoomType() == RoomType.SINGLE && bedNumbers.size() != 1) {

			return new ErrorResult("Tek kişilik odalar için yalnızca 1 yatak girilmelidir.");

		} else if (request.getRoomType() == RoomType.DOUBLE && bedNumbers.size() != 2) {
			return new ErrorResult("Çift kişilik odalar için 2 yatak girilmelidir.");
		}

		for (String bedNumber : bedNumbers) {
			if (bedRepository.existsByBedNumber(bedNumber)) {
				return new ErrorResult("Yatak numarası zaten kullanımda: " + bedNumber);
			}
		}

		Room room = roomMapper.toEntity(request);

		List<Bed> beds = bedNumbers.stream()
				.map(bedNumber -> Bed.builder().bedNumber(bedNumber).isAvailable(true).room(room).build())
				.collect(Collectors.toList());

		room.setBeds(beds);

		roomRepository.save(room);
		return new SuccessResult("Oda ve yatak(lar) başarıyla oluşturuldu.");
	}

	@Override
	public Result updateRoom(Long roomId, RoomUpdateRequest request) {

		Optional<Room> roomOpt = roomRepository.findById(roomId);

		if (roomOpt.isEmpty()) {
			return new ErrorResult("Güncellenmek istenen oda bulunamadı.");
		}

		Room room = roomOpt.get();

		if (room.getRoomNumber().equals(request.getRoomNumber())
				&& roomRepository.existsByRoomNumber(request.getRoomNumber())) {
			return new ErrorResult("Bu oda numarası başka bir oda tarafından kullanılıyor.");
		}

		roomMapper.updateEntityFromDto(room, request);

		roomRepository.save(room);

		return new SuccessResult("Oda başarıyla güncellendi.");
	}

	@Override
	public Result deactivateRoom(Long roomId) {

		Optional<Room> roomOpt = roomRepository.findById(roomId);
		
		if(roomOpt.isEmpty()) {
			return new ErrorResult("Belirtilen ID'ye ait oda bulunamadı.");
		}
		
		Room room = roomOpt.get();
		
		if(!room.isActive()) {
			return new ErrorResult("Bu oda zaten pasif durumda.");
		}
		
		room.setActive(false);
		roomRepository.save(room);
		
		return new SuccessResult("Oda başarıyla pasif hale getirildi.");
	}

	@Override
	public Result addBedToRoom(Long roomId, BedCreateRequest request) {

		Optional<Room> roomOpt = roomRepository.findById(roomId);
		
		if(roomOpt.isEmpty()) {
			return new ErrorResult("Belirtilen ID'ye ait oda bulunamadı.");
		}
		
		Room room = roomOpt.get();
		
		if(!room.isActive()) {
			return new ErrorResult("Pasif durumda olan odaya yatak eklenemez.");
		}
		
		int maxBeds = room.getRoomType() == RoomType.SINGLE ? 1:2;
		
		if(room.getBeds().size() >= maxBeds) {
			return new ErrorResult("Bu oda tipi "+ room.getRoomType() +  " maksimum yatak sayısına ulaştı.");
		}
		
		if (bedRepository.existsByBedNumber(request.getBedNumber())) {
	        return new ErrorResult("Bu yatak numarası sistemde zaten mevcut.");
	    }
		
		boolean bedNumberExistsInRoom = room.getBeds().stream()
				.anyMatch(bed-> bed.getBedNumber().equals(request.getBedNumber()));
		
		if(bedNumberExistsInRoom) {
			return new ErrorResult("Bu yatak numarası bu oda için zaten tanımlı");
		}
		
		Bed newBed = Bed.builder()
	            .bedNumber(request.getBedNumber())
	            .isAvailable(request.isAvailable())
	            .room(room)
	            .build();
		
		room.getBeds().add(newBed);
	    roomRepository.save(room);
	    
	    return new SuccessResult("Yatak başarıyla odaya eklendi.");
	}


}
