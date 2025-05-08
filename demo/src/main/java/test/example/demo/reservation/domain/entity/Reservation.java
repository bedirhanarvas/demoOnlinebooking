package test.example.demo.reservation.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.user.domain.entity.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="bed_id")
    private Bed bed;
	
    private LocalDateTime startingDate;
    
    private LocalDateTime finishingDate;
    
    @Column(unique = true)
    private String reservationNumber;
    
    @Builder.Default
    private boolean isCancelled=false;

    @Builder.Default
    private boolean isActive=true;
    
    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.finishingDate.isBefore(this.startingDate)) {
            throw new IllegalArgumentException("Bitiş günü başlangıç gününden önce olamaz.");
        }
        
        if (this.reservationNumber == null) {
            this.reservationNumber = generateReservationNumber();
        }
    }

    private String generateReservationNumber() {
        return UUID.randomUUID().toString();
    }
}
    
	

