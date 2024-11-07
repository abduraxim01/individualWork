package org.example.individualwork.mapper;

import org.example.individualwork.DTO.MahsulotDTO;
import org.example.individualwork.model.Mahsulot;

import java.util.ArrayList;
import java.util.List;

public class MahsulotMapper {

    public Mahsulot toMahsulot(MahsulotDTO dto) {
        Mahsulot mahsulot = new Mahsulot();
        mahsulot.setBody(dto.getBody());
        mahsulot.setTitle(dto.getTitle());
        mahsulot.setDiscount(dto.getDiscount());
        mahsulot.setPrice(dto.getPrice());
        mahsulot.setImage(dto.getImage());
        mahsulot.setFromDate(dto.getFromDate());
        mahsulot.setToDate(dto.getToDate());
        return mahsulot;
    }

    public MahsulotDTO toMahsulotDTO(Mahsulot mahsulot) {
        MahsulotDTO dto = new MahsulotDTO();
        dto.setId(mahsulot.getId());
        dto.setBody(mahsulot.getBody());
        dto.setTitle(mahsulot.getTitle());
        dto.setDiscount(mahsulot.getDiscount());
        dto.setPrice(mahsulot.getPrice());
        dto.setImage(mahsulot.getImage());
        dto.setFromDate(mahsulot.getFromDate());
        dto.setToDate(mahsulot.getToDate());
        return dto;
    }

    public List<MahsulotDTO> toMahsulotDTOS(List<Mahsulot> mahsulotList) {
        List<MahsulotDTO> dtoList = new ArrayList<MahsulotDTO>();
        for(Mahsulot mahsulot : mahsulotList) {
            dtoList.add(toMahsulotDTO(mahsulot));
        }
        return dtoList;
    }
}
