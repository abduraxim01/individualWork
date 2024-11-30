package org.example.individualwork.service.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.individualwork.DTO.authDTO.AuthLoginDTO;
import org.example.individualwork.mapper.SotuvchiMapper;
import org.example.individualwork.model.Sotuvchi;
import org.example.individualwork.repository.SotuvchiRepository;
import org.example.individualwork.DTO.sotuvchiDTO.SotuvchiDTO;
import org.example.individualwork.exception.SotuvchiExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final SotuvchiRepository sotuvchiRepository;

    final private SotuvchiMapper mapper = new SotuvchiMapper();

    final private Logger logger = LogManager.getLogger(LoginService.class);

    @Autowired
    public LoginService(SotuvchiRepository sotuvchiRepository) {
        this.sotuvchiRepository = sotuvchiRepository;
    }

    public SotuvchiDTO login(AuthLoginDTO authLoginDTO) {
        if (sotuvchiRepository.existsByUsername(authLoginDTO.getUsername())) {

            Sotuvchi sotuvchi = sotuvchiRepository.findByUsername(authLoginDTO.getUsername());
            String passFromBase = sotuvchi.getPassword();

            if (PasswordUtils.checkPassword(authLoginDTO.getPassword(), passFromBase)) {
                logger.info("Username: {}\nPassword: {}", authLoginDTO.getUsername(), authLoginDTO.getPassword());
                return mapper.toSotuvchiDTO(sotuvchi);
            }
            throw new SotuvchiExceptions.InvalidUsernameOrPasswordException("Username/password is invalid");
        }
        throw new SotuvchiExceptions.UserNotFoundException(
                "Username topilmadi: " + authLoginDTO.getUsername());
    }

    public static class PasswordUtils {
        private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        public static boolean checkPassword(String enteredPassword, String storedHashedPassword) {
            return encoder.matches(enteredPassword, storedHashedPassword);
        }
    }

    public Sotuvchi reg(AuthLoginDTO dto){
        return sotuvchiRepository.save(mapper.toSotuvchiForReg(dto));
    }
}
