package org.example.individualwork.service.auth;

import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.mapper.SotuvchiMapper;
import org.example.individualwork.repository.SotuvchiRepository;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private SotuvchiRepository sotuvchiRepository;

    final private SotuvchiMapper mapper = new SotuvchiMapper();

    public SotuvchiDTO login(AuthLoginDTO authLoginDTO) {
        if (sotuvchiRepository.existsByUsername(authLoginDTO.getUsername())) {
            return mapper.toSotuvchiDTO(sotuvchiRepository.findByUsername(authLoginDTO.getUsername()));
        }
        throw new SotuvchiExceptions.UserNotFoundException(
                "Username topilmadi: " + authLoginDTO.getUsername());
    }
}
