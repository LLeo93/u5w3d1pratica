package it.epicode.u5w3d1pratica.service;

import it.epicode.u5w3d1pratica.dto.UserDto;
import it.epicode.u5w3d1pratica.exception.NonTrovatoException;
import it.epicode.u5w3d1pratica.exception.UserNotFoundException;
import it.epicode.u5w3d1pratica.model.User;
import it.epicode.u5w3d1pratica.repository.UserRepository;
import it.epicode.u5w3d1pratica.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserDto userDto){
        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User con id " + id + " non trovato"));
    }

    public User updateUser(int id, UserDto userDto) {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setUsername(userDto.getUsername());
        if (!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(userDaAggiornare);
    }

    public void deleteUser(int id) throws NonTrovatoException {
        User userDaCancellare = userRepository.findById(id)
                .orElseThrow(() -> new NonTrovatoException("Utente con ID " + id + " non trovato"));

        userRepository.delete(userDaCancellare);
    }
}