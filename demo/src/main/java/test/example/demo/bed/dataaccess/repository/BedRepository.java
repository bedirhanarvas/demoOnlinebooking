package test.example.demo.bed.dataaccess.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.room.domain.enums.RoomType;

public interface BedRepository extends JpaRepository<Bed, Long>{

    @Query("""
            SELECT b FROM Bed b
            WHERE b.room.roomType = :roomType
            AND b.id NOT IN (
                SELECT r.bed.id FROM Reservation r
                WHERE r.isCancelled = false
                AND r.finishingDate > :start
                AND r.startingDate < :end
            )
        """)
        List<Bed> findAvailableBeds(@Param("roomType") RoomType roomType,@Param("start") LocalDateTime start,@Param("end") LocalDateTime end);

    @Query("SELECT MAX(b.bedNumber) FROM Bed b")
    Optional<Integer> findMaxBedNumber();
    
    @Query("SELECT COUNT(b) > 0 FROM Bed b WHERE b.bedNumber = :bedNumber")
    boolean existsByBedNumber(@Param("bedNumber") String bedNumber);
    
    Optional<Bed> findByBedNumber(String bedNumber);

    List<Bed> findByRoomId(Long roomId);

    List<Bed> findByIsAvailable(boolean isAvailable);
    
}

