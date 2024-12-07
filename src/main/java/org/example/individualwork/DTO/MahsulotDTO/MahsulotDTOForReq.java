package org.example.individualwork.DTO.MahsulotDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MahsulotDTOForReq {

    private String title;
    private String body;
    private String image;
    private Double price;
    private Double discount;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
