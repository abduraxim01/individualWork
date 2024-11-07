package org.example.individualwork.controller;

import org.example.individualwork.service.MahsulotService;
import org.example.individualwork.DTO.MahsulotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mahsulot")
public class MahsulotController {


    @Autowired
    private MahsulotService mahsulotSer;

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getAllMahsulot")
    public List<MahsulotDTO> getAllMahsulot() {
        return mahsulotSer.getAllMahsulot();
    }

    // admin
    // user
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getAllMahsulotByID")
    public List<MahsulotDTO> getAllMahsulotByID(Long id) {
        return mahsulotSer.getAllMahsulot(id);
    }

    // permit all
    @GetMapping(value = "/getActiveMahsulot")
    public List<MahsulotDTO> getActiveMahsulot() {
        return mahsulotSer.getActiveMahsulot();
    }

    // permit all
    @GetMapping(value = "/getActiveMahsulot/BySotuvchi")
    public List<MahsulotDTO> getActiveMahsulot(Long id) {
        return mahsulotSer.getActiveMahsulot(id);
    }

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getInActiveMahsulot")
    public List<MahsulotDTO> getInActiveMahsulot(Long id) {
        return mahsulotSer.getInActiveMahsulot(id);
    }

    // sotuvchi uchun
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping(value = "/addMahsulot")
    public MahsulotDTO addMahsulot(Long id, MahsulotDTO mahsulotDTO) {
        return mahsulotSer.addMahsulot(id, mahsulotDTO);
    }
}
