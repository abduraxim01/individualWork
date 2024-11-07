package org.example.individualwork.controller.auth;

import org.example.individualwork.service.auth.LoginService;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/login")
public class AuthController {


    @Autowired
    private LoginService loginService;

    @GetMapping
    public ResponseEntity<Object> login(@RequestBody AuthLoginDTO authLoginDTO) {
        try {
            return ResponseEntity.ok(loginService.login(authLoginDTO));
        } catch (SotuvchiExceptions.UserNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        } catch (SotuvchiExceptions.InvalidUsernameOrPasswordException exception) {
            return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
        }
    }

}
