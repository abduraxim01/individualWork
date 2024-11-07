package org.example.individualwork.DTO.sotuvchiDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeSotuvDate {


    private Long id;
    private LocalDateTime localDateTime;
}
