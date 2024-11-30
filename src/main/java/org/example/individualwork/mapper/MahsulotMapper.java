package org.example.individualwork.mapper;

import org.example.individualwork.DTO.MahsulotDTO;
import org.example.individualwork.model.Mahsulot;
import org.example.individualwork.model.Sotuvchi;

import java.util.ArrayList;
import java.util.List;

public class MahsulotMapper {

    public Mahsulot toMahsulot(Sotuvchi sotuvchi, MahsulotDTO dto) {
        return Mahsulot.builder()
                .body(dto.getBody())
                .title(dto.getTitle())
                .discount(dto.getDiscount())
                .price(dto.getPrice())
                .image(dto.getImage())
                .toDate(dto.getToDate())
                .fromDate(dto.getFromDate())
                .sotuvchi(sotuvchi)
                .build();
    }

    public MahsulotDTO toMahsulotDTO(Mahsulot mahsulot) {
        return MahsulotDTO.builder()
//                .id(mahsulot.getId())
                .body(mahsulot.getBody())
                .title(mahsulot.getTitle())
                .discount(mahsulot.getDiscount())
                .sotuvchi_id(mahsulot.getSotuvchi().getId())
                .price(mahsulot.getPrice())
                .image(mahsulot.getImage())
                .fromDate(mahsulot.getFromDate())
                .toDate(mahsulot.getToDate())
                .build();
    }

    public List<MahsulotDTO> toMahsulotDTOS(List<Mahsulot> mahsulotList) {
        List<MahsulotDTO> dtoList = new ArrayList<>();
        for (Mahsulot mahsulot : mahsulotList) {
            dtoList.add(toMahsulotDTO(mahsulot));
        }
        return dtoList;
    }
}
