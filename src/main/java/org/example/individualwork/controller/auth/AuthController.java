package org.example.individualwork.controller.auth;

import org.example.individualwork.DTO.authDTO.AuthRegisterForSotuvDTO;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.model.Sotuvchi;
import org.example.individualwork.service.auth.LoginService;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

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

    @PostMapping(value = "/register")
    public Sotuvchi register(@RequestBody AuthRegisterForSotuvDTO authRegisterForSotuvDTO) {
        return loginService.register(authRegisterForSotuvDTO);
    }
}
