package org.example.individualwork.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.example.individualwork.model.Sotuvchi;
import org.example.individualwork.service.auth.LoginService;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

   @Autowired
    private LoginService loginService;

    @GetMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthLoginDTO authLoginDTO) {
        try {
            return ResponseEntity.ok(loginService.login(authLoginDTO));
        } catch (SotuvchiExceptions.UserNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.InvalidUsernameOrPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

    @PostMapping(value = "/reg")
    public Sotuvchi reg(@RequestBody AuthLoginDTO dto) {
        return loginService.reg(dto);
    }
}
