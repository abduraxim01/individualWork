package org.example.individualwork.service;

import org.apache.logging.log4j.LogManager;
import org.example.individualwork.DTO.sotuvchiDTO.ChangeSotuvDate;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvOzgarQiymat;
import org.example.individualwork.mapper.SotuvchiMapper;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.DTO.authDTO.AuthRegisterForSotuvDTO;
import org.example.individualwork.repository.SotuvchiRepository;
import org.example.individualwork.model.Sotuvchi;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.Logger;

@Service
public class SotuvchiService {

    @Autowired
    private final SotuvchiRepository sotuvchiRep;

    final private SotuvchiMapper sotuvchiMap = new SotuvchiMapper();

    final private Logger logger = LogManager.getLogger(SotuvchiService.class);

    public SotuvchiService(SotuvchiRepository sotuvchiRep) {
        this.sotuvchiRep = sotuvchiRep;
    }

    // admin
    // barcha sotuvchilar ro'yhatini olish
    public List<SotuvchiDTO> getAllSotuvchiAsDTO() {
        return sotuvchiMap.toSotuvchiDTO(sotuvchiRep.findAll());
    }

    public List<Sotuvchi> getAllSotuvchi() {
        return sotuvchiRep.findAll();
    }

    // permit all
    // active sotuvchilar ro'yhatini olish
    public List<SotuvchiDTO> getActiveSotuvchi() {
        List<Sotuvchi> activeSotuvchiList = getAllSotuvchi();
        activeSotuvchiList.removeIf(s ->s.getRole().toString().equals("ADMIN"));
        return sotuvchiMap.toSotuvchiDTO(activeSotuvchiList);
    }

    // admin uchun
    // inactive sotuvchilar ro'yhatini olish
    public List<SotuvchiDTO> getInactiveSotuvchi() {
        List<Sotuvchi> inActiveSotuvchiList = getAllSotuvchi();

        if (inActiveSotuvchiList == null) {
            throw new NullPointerException("The list of Sotuvchi is null. Expected a non-null list from getAllSotuvchi().");
        }

        inActiveSotuvchiList.removeIf(s -> {
            if (s.getToDate() == null) {
                throw new NullPointerException("Encountered a Sotuvchi with a null 'toDate' field.");
            }
            return LocalDateTime.now().isBefore(s.getToDate());
        });

        return sotuvchiMap.toSotuvchiDTO(inActiveSotuvchiList);
    }

    // admin uchun
    // yangi sotuvchi qo'shish
    public Sotuvchi addSotuvchi(AuthRegisterForSotuvDTO authRegForSotuvDTO) {
        existsByUsername(authRegForSotuvDTO.getUsername());
        timeChecker(authRegForSotuvDTO.getToDate());
        return sotuvchiRep.save(sotuvchiMap.toSotuvchiForRegis(authRegForSotuvDTO));
    }

    // sotuvchi uchun
    // sotuvchini username, password, name va image ni o'zgartirish
    public SotuvchiDTO changeSotuvchiDetails(SotuvOzgarQiymat sotuvOzgarQiymat) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        existsByUsername(sotuvOzgarQiymat.getUsername());
        logger.info("UserID: {} \n User details were changed", ((Sotuvchi) authentication.getPrincipal()).getId());
        return sotuvchiMap.toSotuvchiDTO(sotuvchiRep.save(
                sotuvchiMap.changeSotuvchiDetails((Sotuvchi) authentication.getPrincipal(), sotuvOzgarQiymat)));
    }

    private void existsByUsername(String username) {
        if (sotuvchiRep.existsByUsername(username)) {
            logger.error("Username oldindan mavjud: {}", username);
            throw new SotuvchiExceptions
                    .UsernameAlreadyTakenException("Username oldindan mavjud: " + username);
        }
    }

    // admin uchun
    // sotuvchini sotuv muddatini uzaytirish
    public String changeSotuvchiDate(ChangeSotuvDate changeSotuvDate) {
        Sotuvchi sotuvchi = ((sotuvchiRep.findById(changeSotuvDate.getId())).get());
        timeChecker(changeSotuvDate.getLocalDateTime());
        sotuvchi.setToDate(changeSotuvDate.getLocalDateTime());
        logger.info("User id: {}\ntoDate: {}", sotuvchi.getId(), changeSotuvDate.getLocalDateTime());
        return sotuvchiRep.save(sotuvchi).getToDate().toString();
    }

    private void timeChecker(LocalDateTime localDateTime) {
        if (localDateTime == null ||
                localDateTime.isBefore(LocalDateTime.now())) {
            throw new SotuvchiExceptions
                    .IllegalArgumentException("Muddat null yoki eski vaqtni ifodalaydi: " + localDateTime);
        }
    }
}
