package test.example.demo.room.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.room.domain.enums.RoomType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomUpdateRequest {
	
    @NotBlank(message = "Oda numarası boş olamaz.")
    private String roomNumber;

    @NotNull(message = "Oda tipi boş olamaz.")
    private RoomType roomType;

}
