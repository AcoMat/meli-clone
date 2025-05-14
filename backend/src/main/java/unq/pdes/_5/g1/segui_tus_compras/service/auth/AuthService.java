package unq.pdes._5.g1.segui_tus_compras.service.auth;

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
    private final UsersRepository usersRepository;
    private final Mapper mapper;
    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsersRepository usersRepository, Mapper mapper, JwtTokenProvider jwtTokenProvider) {
        this.usersRepository = usersRepository;
        this.mapper = mapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthResponseDTO register(RegisterData registerData) {

        if (usersRepository.existsByEmail(registerData.getEmail())) {
            throw new AlreadyExistingUser("User already exists");
        }

        User new_user = usersRepository.save(
                new User(
                        registerData.getFirstName(),
                        registerData.getLastName(),
                        registerData.getEmail(),
                        passwordEncoder.encode(registerData.getPassword())
                )
        );

        return new AuthResponseDTO(mapper.toDTO(new_user), jwtTokenProvider.generateToken(new_user.getId()));
    }

    public AuthResponseDTO login(LoginCredentials credentials){
        User user = usersRepository.findByEmail(credentials.email);
        if (user == null || !passwordEncoder.matches(credentials.password, user.getPassword())) {
            throw new IllegalArgumentException("Email or password is incorrect");
        }

        return new AuthResponseDTO(mapper.toDTO(user), jwtTokenProvider.generateToken(user.getId()));
    }
}
