package org.example.individualwork.controller;

import org.example.individualwork.service.SotuvchiService;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvOzgarQiymat;
import org.example.individualwork.DTO.sotuvchiDTO.ChangeSotuvDate;
import org.example.individualwork.DTO.authDTO.AuthRegisterForSotuvDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sotuvchi", consumes = MediaType.APPLICATION_JSON_VALUE)
public class SotuvchiController {

    @Autowired
    private SotuvchiService sotuvchiSer;

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getAllSotuvchiAsDTO")
    public ResponseEntity<Object> getAllSotuvchiAsDTO() {
        try {
            return ResponseEntity.ok(sotuvchiSer.getAllSotuvchiAsDTO());
        } catch (SotuvchiExceptions.Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @GetMapping(value = "/getInactiveSotuvchi")
    public ResponseEntity<Object> getInactiveSotuvchi() {
        try {
            return ResponseEntity.ok(sotuvchiSer.getInactiveSotuvchi());
        } catch (SotuvchiExceptions.Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // permit all
    @GetMapping(value = "/getActiveSotuvchi")
    public ResponseEntity<Object> getActiveSotuvchi() {
        try {
            return ResponseEntity.ok(sotuvchiSer.getActiveSotuvchi());
        } catch (SotuvchiExceptions.Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping(value = "/addSotuvchi")
    public ResponseEntity<Object> addSotuvchi(@RequestBody AuthRegisterForSotuvDTO authRegisterForSotuvDTO) {
        try {
            return ResponseEntity.ok(sotuvchiSer.addSotuvchi(authRegisterForSotuvDTO));
        } catch (SotuvchiExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // user
    @PreAuthorize(value = "hasRole('USER')")
    @PostMapping(value = "/changeSotuvchiDetails")
    public ResponseEntity<Object> changeSotuvchiDetails(@RequestBody SotuvOzgarQiymat sotuvOzgarQiymat) {
        try {
            return ResponseEntity.ok(sotuvchiSer.changeSotuvchiDetails(sotuvOzgarQiymat));
        } catch (SotuvchiExceptions.UsernameAlreadyTakenException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.AccountExpiredException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    // admin
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping(value = "/changeSotuvchiDate")
    public ResponseEntity<String> changeSotuvchiDate(@RequestBody ChangeSotuvDate changeSotuvDate) {
        try {
            return ResponseEntity.ok("Muddat: " + sotuvchiSer.changeSotuvchiDate(changeSotuvDate));
        } catch (SotuvchiExceptions.IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }
}
