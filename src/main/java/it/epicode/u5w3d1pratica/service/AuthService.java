package it.epicode.u5w3d1pratica.service;

import it.epicode.u5w3d1pratica.dto.LoginDto;
import it.epicode.u5w3d1pratica.exception.NonTrovatoException;
import it.epicode.u5w3d1pratica.model.User;
import it.epicode.u5w3d1pratica.repository.UserRepository;
import it.epicode.u5w3d1pratica.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    public String login(LoginDto loginDto) throws NonTrovatoException {
        User user = userRepository.findByEmail(loginDto.getEmail()).
                orElseThrow(() -> new NonTrovatoException("Email/password non trovati"));

        if(user.getPassword().equals(loginDto.getPassword())){
            //creare il token
           return jwtTool.createToken(user);
        }
        else{
            throw new NonTrovatoException("Email/password non trovati");
        }
    }
}
