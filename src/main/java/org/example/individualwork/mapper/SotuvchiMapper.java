package org.example.individualwork.mapper;

import org.apache.logging.log4j.LogManager;
import org.example.individualwork.DTO.authDTO.AuthRegisterForSotuvDTO;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvOzgarQiymat;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.model.Rollar;
import org.example.individualwork.model.Sotuvchi;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SotuvchiMapper {

    final private MahsulotMapper mahsulotMapper = new MahsulotMapper();

    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Sotuvchi changeSotuvchiDetails(Sotuvchi optionalSotuvchi, SotuvOzgarQiymat ozgarQiymat) {
        optionalSotuvchi.setUsername(ozgarQiymat.getUsername());
        optionalSotuvchi.setPassword(passwordEncoder.encode(ozgarQiymat.getPassword()));
        optionalSotuvchi.setImage(ozgarQiymat.getImage());
        optionalSotuvchi.setName(ozgarQiymat.getName());
        return optionalSotuvchi;
    }

    public Sotuvchi toSotuvchiForRegis(AuthRegisterForSotuvDTO authRegisterForSotuvDTO) {
        return Sotuvchi.builder()
                .username(authRegisterForSotuvDTO.getUsername())
                .password(passwordEncoder.encode(authRegisterForSotuvDTO.getPassword()))
                .toDate(authRegisterForSotuvDTO.getToDate())
                .role(Rollar.USER)
                .build();
    }

    public Sotuvchi toSotuvchiForReg(AuthLoginDTO authLoginDTO) {
        return Sotuvchi.builder()
                .username(authLoginDTO.getUsername())
                .password(passwordEncoder.encode(authLoginDTO.getPassword()))
                .toDate(LocalDateTime.of(2025,12,14,15,12,11))
                .role(Rollar.ADMIN)
                .build();
    }

    public SotuvchiDTO toSotuvchiDTO(Sotuvchi s) {
        return SotuvchiDTO.builder()
                .id(s.getId())
                .username(s.getUsername())
                .image(s.getImage())
                .mahsulotDTOS(mahsulotMapper.toMahsulotDTOS(s.getMahsulots()))
                .created_at(s.getCreated_at())
                .toDate(s.getToDate())
                .build();
    }

    public List<SotuvchiDTO> toSotuvchiDTO(List<Sotuvchi> sotuvchiList) {
        List<SotuvchiDTO> dtos = new ArrayList<SotuvchiDTO>();
        for (Sotuvchi s : sotuvchiList) {
            dtos.add(toSotuvchiDTO(s));
        }
        return dtos;
    }
}
