package org.example.individualwork.controller;

import org.example.individualwork.exception.SotuvchiExceptions;
import org.example.individualwork.service.MahsulotService;
import org.example.individualwork.DTO.MahsulotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/mahsulot")
public class MahsulotController {


    @Autowired
    private MahsulotService mahsulotSer;

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getAllMahsulot")
    public ResponseEntity<Object> getAllMahsulot() {
        try {
            return ResponseEntity.ok(mahsulotSer.getAllMahsulot());
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
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
    public ResponseEntity<Object> getActiveMahsulot() {
        try {
            return ResponseEntity.ok(mahsulotSer.getActiveMahsulot());
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // permit all
    @GetMapping(value = "/getActiveMahsulot/{id}")
    public ResponseEntity<Object> getActiveMahsulot(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(mahsulotSer.getActiveMahsulot(id));
        } catch (SotuvchiExceptions.NullPointerException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getInActiveMahsulot/{id}")
    public ResponseEntity<Object> getInActiveMahsulot(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(mahsulotSer.getInActiveMahsulot(id));
        } catch (SotuvchiExceptions.NullPointerException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // sotuvchi uchun
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping(value = "/addMahsulot")
    public ResponseEntity<Object> addMahsulot(@RequestBody MahsulotDTO mahsulotDTO) {
        try {
            return ResponseEntity.ok(mahsulotSer.addMahsulot(mahsulotDTO));
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
