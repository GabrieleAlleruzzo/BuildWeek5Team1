package epicode.BW5T1.service;

import epicode.BW5T1.dto.LoginDto;
import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.User;
import epicode.BW5T1.repository.UserRepository;
import epicode.BW5T1.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername()).
                orElseThrow(() -> new NotFoundException("Utente con questo username/password non trovato"));

        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){

            return jwtTool.createToken(user);
        }
        else{
            throw new NotFoundException("Utente con questo username/password non trovato");
        }
    }
}