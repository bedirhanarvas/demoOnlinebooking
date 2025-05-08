package test.example.demo.room.application.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.room.domain.enums.RoomType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomCreateRequest {
	
    @NotBlank(message = "Oda numarası boş olamaz.")
    private String roomNumber;
    
    @NotNull(message = "Yatak numaraları boş olamaz.")
    @Size(min = 1, max = 2, message = "En az bir yatak numarası girilmelidir.")
    @NotBlank(message = "Yatak numarası boş olamaz.")
    private List<String> bedNumbers;

    @NotNull(message = "Oda tipi seçilmelidir.")
    private RoomType roomType;

}
