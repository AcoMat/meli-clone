package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.AlreadyExistingUser;
import unq.pdes._5.g1.segui_tus_compras.mapper.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;

@Service
public class AuthService {
    @Autowired private UsersRepository usersRepository;
    @Autowired private Mapper mapper;

    @Autowired private PasswordEncoder passwordEncoder;


    public AuthResponseDTO register(RegisterData registerData) {

        if (usersRepository.existsByEmail(registerData.getEmail())) {
            throw new AlreadyExistingUser("User already exists");
        }

        User new_user = mapper.toEntity(registerData);
        new_user.setPassword(passwordEncoder.encode(registerData.getPassword()));

        usersRepository.save(new_user);

        return new AuthResponseDTO(mapper.toDTO(new_user), JwtTokenProvider.generateToken(new_user.getId()));
    }

    public AuthResponseDTO login(LoginCredentials credentials){
        User user = usersRepository.findByEmail(credentials.getEmail());
        if (user == null || !passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Email or password is incorrect");
        }

        return new AuthResponseDTO(mapper.toDTO(user), JwtTokenProvider.generateToken(user.getId()));
    }
}
