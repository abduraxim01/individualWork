package org.example.individualwork.controller;

import org.example.individualwork.DTO.MahsulotDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.example.individualwork.service.MahsulotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/mahsulot")
public class MahsulotController {

    @Autowired
    private MahsulotService mahsulotSer;

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getAllMahsulot")
    public ResponseEntity<List<MahsulotDTO>> getAllMahsulot() {
        return ResponseEntity.ok(mahsulotSer.getAllMahsulot());
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
    public ResponseEntity<List<MahsulotDTO>> getActiveMahsulot() {
        return ResponseEntity.ok(mahsulotSer.getActiveMahsulot());
    }

    // permit all
    @GetMapping(value = "/getActiveMahsulot/ByID/{id}")
    public List<MahsulotDTO> getActiveMahsulot(@PathVariable(name = "id") Long id) {
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
    public ResponseEntity<Object> addMahsulot(@RequestBody MahsulotDTO mahsulotDTO) {
        try {
            return ResponseEntity.ok(mahsulotSer.addMahsulot(mahsulotDTO));
        } catch (SotuvchiExceptions.Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}