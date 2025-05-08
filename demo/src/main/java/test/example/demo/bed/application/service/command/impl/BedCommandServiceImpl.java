package test.example.demo.bed.application.service.command.impl;


import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedCreateRequest;
import test.example.demo.bed.application.service.command.BedCommandService;
import test.example.demo.bed.dataaccess.repository.BedRepository;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.ErrorResult;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.core.utilities.results.SuccessResult;
import test.example.demo.reservation.dataaccess.repository.ReservationRepository;
import test.example.demo.room.dataaccess.repository.RoomRepository;
import test.example.demo.room.domain.entity.Room;

@Service
@RequiredArgsConstructor
public class BedCommandServiceImpl implements BedCommandService{
	
	private final BedRepository bedRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public Result createBed(BedCreateRequest request) {
        if (bedRepository.existsByBedNumber(request.getBedNumber())) {
            return new ErrorResult("Bu yatak numarası sistemde zaten mevcut.");
        }

        Bed bed = Bed.builder()
                .bedNumber(request.getBedNumber())
                .isAvailable(request.isAvailable())
                .build();

        bedRepository.save(bed);

        return new SuccessResult("Yatak başarıyla oluşturuldu.");
    }
    
    @Override
    @Transactional
    public Result updateBedStatus(Long bedId, boolean isAvailable) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new IllegalArgumentException("Yatak bulunamadı"));

        bed.setAvailable(isAvailable);
        bedRepository.save(bed);

        return new SuccessResult("Yatak durumu başarıyla güncellendi.");
    }
    
    @Override
    @Transactional
    public Result deleteBed(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new IllegalArgumentException("Yatak bulunamadı"));

        if (reservationRepository.existsByBedId(bedId)) {
            return new ErrorResult("Bu yatak rezervasyona sahip, silinemez.");
        }

        bedRepository.delete(bed);
        return new SuccessResult("Yatak başarıyla silindi.");
    }

    @Override
    @Transactional
    public Result assignBedToRoom(Long roomId, BedCreateRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Oda bulunamadı"));

        Bed bed = Bed.builder()
                .bedNumber(request.getBedNumber())
                .isAvailable(request.isAvailable())
                .room(room)
                .build();

        room.getBeds().add(bed);
        roomRepository.save(room);

        return new SuccessResult("Yatak başarıyla odaya atandı.");
    }

    @Override
    @Transactional
    public Result unassignBedFromRoom(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new IllegalArgumentException("Yatak bulunamadı"));

        Room room = bed.getRoom();
        if (room != null) {
            room.getBeds().remove(bed);
            roomRepository.save(room);
        }

        bed.setRoom(null);
        bedRepository.save(bed);

        return new SuccessResult("Yatak başarıyla odadan çıkarıldı.");
    }
}
