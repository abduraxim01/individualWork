package org.example.individualwork.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.individualwork.DTO.MahsulotDTO;
import org.example.individualwork.mapper.MahsulotMapper;
import org.example.individualwork.model.Mahsulot;
import org.example.individualwork.repository.MahsulotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class MahsulotService {

    @Autowired
    private MahsulotRepository mahsulotRep;

    MahsulotMapper mahsulotMapper = new MahsulotMapper();

    //
    // barcha mahsulot ro'yhatini olish
    public List<MahsulotDTO> getAllMahsulot() {
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
        for (MahsulotDTO s : activeMahsulotList) {
            if (LocalDateTime.now().isAfter(s.getToDate()) || LocalDateTime.now().isBefore(s.getFromDate())) {
                activeMahsulotList.remove(s);
            }
        }
        return activeMahsulotList;
    }

    // ma'lum bir usernamega tegishli  activemahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getActiveMahsulot(Long id) {
        List<MahsulotDTO> activeMahsulotList = getAllMahsulot();
        for (MahsulotDTO s : activeMahsulotList) {
            if (LocalDateTime.now().isAfter(s.getToDate())
                    || LocalDateTime.now().isBefore(s.getFromDate())
                    || !s.getSotuvchi_id().equals(id)) {
                activeMahsulotList.remove(s);
            }
        }
        return activeMahsulotList;
    }

    // admin uchun
    // inactivemahsulotlar ro'yhatini olish
//    public List<MahsulotDTO> getInActiveMahsulot() {
//        List<MahsulotDTO> activeMahsulotList = getAllMahsulot();
//        for (MahsulotDTO s : activeMahsulotList) {
//            if (LocalDateTime.now().isBefore(s.getToDate()) && LocalDateTime.now().isAfter(s.getFromDate())) {
//                activeMahsulotList.remove(s);
//            }
//        }
//        return activeMahsulotList;
//    }

    // ma'lum bir usernamega tegishli  inactivemahsulotlar ro'yhatini olish
    public List<MahsulotDTO> getInActiveMahsulot(Long id) {
        List<MahsulotDTO> activeMahsulotList = getAllMahsulot();
        for (MahsulotDTO s : activeMahsulotList) {
            if (LocalDateTime.now().isBefore(s.getToDate())
                    && LocalDateTime.now().isAfter(s.getFromDate())
                    && !s.getSotuvchi_id().equals(id)) {
                activeMahsulotList.remove(s);
            }
        }
        return activeMahsulotList;
    }

    // sotuvchi uchun
    // mahsulot qo'shish
    public MahsulotDTO addMahsulot(Long id, MahsulotDTO mahsulotDTO) {
        mahsulotDTO.setSotuvchi_id(id);
        Mahsulot mahsulot = mahsulotMapper.toMahsulot(mahsulotDTO);
        return mahsulotMapper.toMahsulotDTO(mahsulotRep.save(mahsulot));
    }
}
