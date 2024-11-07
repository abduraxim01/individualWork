package org.example.individualwork.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MahsulotDTO {


    private Long id;
    private String title;
    private String body;
    private String image;
    private Long sotuvchi_id;
    private Double price;
    private Double discount;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
