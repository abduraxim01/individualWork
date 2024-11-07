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
    private SotuvchiRepository sotuvchiRep;

    final private SotuvchiMapper sotuvchiMap = new SotuvchiMapper();

    Logger logger = LogManager.getLogger(SotuvchiService.class);

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
        for (Sotuvchi s : activeSotuvchiList) {
            if (LocalDateTime.now().isAfter(s.getToDate())) {
                activeSotuvchiList.remove(s);
            }
        }
        return sotuvchiMap.toSotuvchiDTO(activeSotuvchiList);
    }

    // admin uchun
    // inactive sotuvchilar ro'yhatini olish
    public List<SotuvchiDTO> getInactiveSotuvchi() {
        List<Sotuvchi> inActiveSotuvchiList = getAllSotuvchi();
        for (Sotuvchi s : inActiveSotuvchiList) {
            if (LocalDateTime.now().isBefore(s.getToDate())) {
                inActiveSotuvchiList.remove(s);
            }
        }
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
        return sotuvchiMap.toSotuvchiDTO(sotuvchiRep.save(
                sotuvchiMap.changeSotuvchiDetails((Sotuvchi) authentication.getPrincipal(), sotuvOzgarQiymat)));
    }

    private void existsByUsername(String username) {
        if (sotuvchiRep.existsByUsername(username)) {
            logger.error("Username oldindan mavjud: " + username);
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
        logger.info("User id: " + sotuvchi.getId() + "\n"
                + "toDate: " + changeSotuvDate.getLocalDateTime());
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
