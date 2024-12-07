package org.example.individualwork.mapper;

import org.example.individualwork.DTO.MahsulotDTO.MahsulotDTO;
import org.example.individualwork.DTO.MahsulotDTO.MahsulotDTOForReq;
import org.example.individualwork.model.Mahsulot;
import org.example.individualwork.model.Sotuvchi;

import java.util.ArrayList;
import java.util.List;

public class MahsulotMapper {

    public Mahsulot toMahsulot(Sotuvchi sotuvchi, MahsulotDTOForReq mahsulotDTOForReq) {
        return Mahsulot.builder()
                .body(mahsulotDTOForReq.getBody())
                .title(mahsulotDTOForReq.getTitle())
                .discount(mahsulotDTOForReq.getDiscount())
                .price(mahsulotDTOForReq.getPrice())
                .image(mahsulotDTOForReq.getImage())
                .toDate(mahsulotDTOForReq.getToDate())
                .fromDate(mahsulotDTOForReq.getFromDate())
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
