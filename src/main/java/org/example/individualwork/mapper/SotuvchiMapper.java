package org.example.individualwork.mapper;

import org.example.individualwork.DTO.authDTO.AuthRegisterForSotuvDTO;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvOzgarQiymat;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.model.Rollar;
import org.example.individualwork.model.Sotuvchi;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SotuvchiMapper {

    final private Logger logger = Logger.getLogger(SotuvchiMapper.class.getName());

    final private MahsulotMapper mahsulotMapper = new MahsulotMapper();

    final private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Sotuvchi changeSotuvchiDetails(Sotuvchi optionalSotuvchi, SotuvOzgarQiymat ozgarQiymat) {
        optionalSotuvchi.setUsername(ozgarQiymat.getUsername());
        optionalSotuvchi.setPassword(passwordEncoder.encode(ozgarQiymat.getPassword()));
        optionalSotuvchi.setImage(ozgarQiymat.getImage());
        optionalSotuvchi.setName(ozgarQiymat.getName());
        logger.info("User id: " + optionalSotuvchi.getId() + "\n"
                + "User details were changed");
        return optionalSotuvchi;
    }

    public Sotuvchi toSotuvchiForRegis(AuthRegisterForSotuvDTO authRegisterForSotuvDTO) {
        Sotuvchi s = new Sotuvchi();
        s.setUsername(authRegisterForSotuvDTO.getUsername());
        s.setPassword(passwordEncoder.encode(authRegisterForSotuvDTO.getPassword()));
        s.setToDate(authRegisterForSotuvDTO.getToDate());
        s.setRole(Rollar.USER);
        return s;
    }

    public Sotuvchi toSotuvchiForReg(AuthLoginDTO authLoginDTO) {
        Sotuvchi s = new Sotuvchi();
        s.setUsername(authLoginDTO.getUsername());
        s.setPassword(passwordEncoder.encode(authLoginDTO.getPassword()));
        s.setRole(Rollar.ADMIN);
        return s;
    }

    public SotuvchiDTO toSotuvchiDTO(Sotuvchi s) {
        SotuvchiDTO dto = new SotuvchiDTO();
        dto.setUsername(s.getUsername());
        dto.setPassword(s.getPassword());
        dto.setImage(s.getImage());
        dto.setMahsulotDTOS(mahsulotMapper.toMahsulotDTOS(s.getMahsulots()));
        dto.setCreated_at(s.getCreated_at());
        dto.setToDate(s.getToDate());
        return dto;
    }

    public List<SotuvchiDTO> toSotuvchiDTO(List<Sotuvchi> sotuvchiList) {
        List<SotuvchiDTO> dtos = new ArrayList<SotuvchiDTO>();
        for (Sotuvchi s : sotuvchiList) {
            dtos.add(toSotuvchiDTO(s));
        }
        return dtos;
    }
}
