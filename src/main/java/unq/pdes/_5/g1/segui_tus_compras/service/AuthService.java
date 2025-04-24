package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.exception.AlredyExistingUserExceptions;
import unq.pdes._5.g1.segui_tus_compras.mapper.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserLoginDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserRegisterDTO;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

@Service
public class AuthService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Mapper mapper;

    public UserDTO login(UserLoginDTO userDTO) {
        User user = usersRepository.findByEmail(userDTO.email);
        if (user == null || !user.getPassword().equals(userDTO.password)) {
            throw new AlredyExistingUserExceptions("Invalid email or password");
        }
        return mapper.toDTO(user);
    }

    public UserDTO register(UserRegisterDTO userDTO) {
        if (usersRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlredyExistingUserExceptions("User already exists");
        }

        User new_user = mapper.toEntity(userDTO);
        usersRepository.save(new_user);
        return mapper.toDTO(new_user);
    }
}
