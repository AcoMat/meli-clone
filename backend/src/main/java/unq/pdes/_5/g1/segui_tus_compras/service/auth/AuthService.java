package unq.pdes._5.g1.segui_tus_compras.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.AlreadyExistingUser;
import unq.pdes._5.g1.segui_tus_compras.mapper.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.AuthResponseDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.LoginCredentials;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;

@Service
public class AuthService {
    @Autowired private UsersRepository usersRepository;
    @Autowired private Mapper mapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AuthResponseDTO register(RegisterData registerData) {

        if (usersRepository.existsByEmail(registerData.getEmail())) {
            throw new AlreadyExistingUser("User already exists");
        }

        User new_user = new User(
                registerData.getFirstName(),
                registerData.getLastName(),
                registerData.getEmail(),
                passwordEncoder.encode(registerData.getPassword())
        );

        usersRepository.save(new_user);

        return new AuthResponseDTO(mapper.toDTO(new_user), JwtTokenProvider.generateToken(new_user.getId()));
    }

    public AuthResponseDTO login(LoginCredentials credentials){
        User user = usersRepository.findByEmail(credentials.email);
        if (user == null || !passwordEncoder.matches(credentials.password, user.getPassword())) {
            throw new IllegalArgumentException("Email or password is incorrect");
        }

        return new AuthResponseDTO(mapper.toDTO(user), JwtTokenProvider.generateToken(user.getId()));
    }
}
