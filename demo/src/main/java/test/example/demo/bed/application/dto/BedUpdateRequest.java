package test.example.demo.bed.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedUpdateRequest {
    @NotBlank(message = "Yatak numarası boş olamaz.")
    private String bedNumber;
    
    private boolean isAvailable;
}

