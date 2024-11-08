package org.example.individualwork.DTO.sotuvchiDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.individualwork.DTO.MahsulotDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SotuvchiDTO {

    private Long id;
    private String username;
    private String password;
    private String image;
    private List<MahsulotDTO> mahsulotDTOS;
    private LocalDateTime toDate;
    private Timestamp created_at;
}
