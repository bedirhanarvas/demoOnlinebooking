package test.example.demo.room.domain.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.room.domain.enums.RoomType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@NotNull(message = "Oda numarası boş olamaz.")
	private String roomNumber;
	
	@Enumerated(EnumType.STRING)
	private RoomType roomType;
	
	@Builder.Default
    private boolean isActive = true;
	
	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Bed> beds;
	
}
