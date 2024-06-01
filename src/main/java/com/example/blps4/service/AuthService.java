package com.example.blps4.service;

import com.example.blps4.dto.request.UserDto;
import com.example.blps4.entity.UsersEntity;
import com.example.blps4.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    @Transactional(rollbackFor = {Exception.class})
    public void registerUser(UserDto userDto) throws Exception {
        String username = userDto.getUsername();

        if (usersRepository.existsByUsername(userDto.getUsername())) {
            throw new Exception("User with username " + username + " exist");
        }

        UsersEntity user = new UsersEntity(userDto.getUsername(), userDto.getEmail(), encoder.encode(userDto.getPassword()), userDto.getRole());
        usersRepository.save(user);
    }


    public Integer loginUser(UserDto userDto) throws Exception {
        String username = userDto.getUsername();
        Optional<UsersEntity> user = usersRepository.findByUsername(username);

        if (user.isEmpty()) {
            throw new Exception("User with username " + username + " not found");
        }

        return user.get().getId();
    }
}
