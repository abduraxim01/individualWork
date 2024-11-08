package org.example.individualwork.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.example.individualwork.DTO.MahsulotDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.example.individualwork.mapper.MahsulotMapper;
import org.example.individualwork.model.Mahsulot;
import org.example.individualwork.model.Sotuvchi;
import org.example.individualwork.repository.MahsulotRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MahsulotService {

    private MahsulotRepository mahsulotRep;

    final private Logger logger = LogManager.getLogger(MahsulotService.class);

    MahsulotMapper mahsulotMapper = new MahsulotMapper();

    @Autowired
    public MahsulotService(MahsulotRepository mahsulotRep) {
        this.mahsulotRep = mahsulotRep;
    }

    //
    // barcha mahsulot ro'yhatini olish
    public List<MahsulotDTO> getAllMahsulot() throws SotuvchiExceptions.IllegalArgumentException {
        return mahsulotMapper.toMahsulotDTOS(mahsulotRep.findAll());
    }

    // ma'lum bir usernamega tegishli  barcha mahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getAllMahsulot(Long id) {
        return mahsulotMapper.toMahsulotDTOS(mahsulotRep.findAll().stream().filter(
                        mahsulot -> mahsulot.getSotuvchi().getId().equals(id))
                .collect(Collectors.toList()));
    }

    // active mahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getActiveMahsulot() {
        List<MahsulotDTO> activeMahsulotList = getAllMahsulot();
        activeMahsulotList.removeIf(s -> LocalDateTime.now().isAfter(s.getToDate())
                || LocalDateTime.now().isBefore(s.getFromDate()));
        return activeMahsulotList;
    }

    // ma'lum bir usernamega tegishli  activemahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getActiveMahsulot(Long id) {
        List<MahsulotDTO> allMahsulot = getAllMahsulot();
        allMahsulot.removeIf(s -> LocalDateTime.now().isAfter(s.getToDate())
                || LocalDateTime.now().isBefore(s.getFromDate()));
        allMahsulot.removeIf(s -> !s.getSotuvchi_id().equals(id));
        logger.info("UserID: {} ActiveMahsulot", id);
        return allMahsulot;
    }

    // ma'lum bir usernamega tegishli  inactivemahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getInActiveMahsulot(Long id) {
        List<MahsulotDTO> allMahsulot = getAllMahsulot();
        allMahsulot.removeIf(s -> LocalDateTime.now().isBefore(s.getToDate())
                && LocalDateTime.now().isAfter(s.getFromDate()));
        allMahsulot.removeIf(s -> !s.getSotuvchi_id().equals(id));
        logger.info("UserID: {} InActiveMahsulot", id);
        return allMahsulot;
    }

    // sotuvchi uchun
    // mahsulot qo'shish
    public MahsulotDTO addMahsulot(MahsulotDTO mahsulotDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (mahsulotDTO == null) {
            throw new SotuvchiExceptions.IllegalArgumentException("Mahsulot null bo'la olmaydi");
        }
        Arrays.asList(
                mahsulotDTO.getTitle(),
                mahsulotDTO.getPrice(),
                mahsulotDTO.getFromDate(),
                mahsulotDTO.getDiscount(),
                mahsulotDTO.getToDate()
        ).forEach(field -> {
            if (field == null) {
                throw new SotuvchiExceptions.IllegalArgumentException("Mahsulotda null field  mavjud");
            }
        });
        if (mahsulotDTO.getFromDate().isAfter(mahsulotDTO.getToDate())
                || mahsulotDTO.getToDate().isBefore(LocalDateTime.now())
                || mahsulotDTO.getFromDate().isBefore(LocalDateTime.now())) {
            throw new SotuvchiExceptions.IllegalArgumentException("Mahsulotni amal qilish muddati noto'g'ri");
        }
        Mahsulot mahsulot = mahsulotMapper.toMahsulot(
                ((Sotuvchi) authentication.getPrincipal()), mahsulotDTO);
        logger.info("UserID: {} \n Mahsulot title: {}",
                ((Sotuvchi) authentication.getPrincipal()).getId(), mahsulotDTO.getTitle());
        return mahsulotMapper.toMahsulotDTO(mahsulotRep.save(mahsulot));
    }
}
