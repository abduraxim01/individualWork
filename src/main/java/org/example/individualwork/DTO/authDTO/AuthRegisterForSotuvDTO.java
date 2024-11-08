package org.example.individualwork.DTO.authDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterForSotuvDTO {

    private String username;
    private String password;
    private LocalDateTime toDate;
}
