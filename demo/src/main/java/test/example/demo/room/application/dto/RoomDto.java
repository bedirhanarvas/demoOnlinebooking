package test.example.demo.room.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.room.domain.enums.RoomType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto {

    private Long id;
    private String roomNumber;
    private RoomType roomType;
    private boolean isActive;
    private List<BedDto> beds;
}