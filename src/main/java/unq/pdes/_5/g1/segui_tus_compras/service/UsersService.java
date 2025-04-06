package unq.pdes._5.g1.segui_tus_compras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.pdes._5.g1.segui_tus_compras.mappers.Mapper;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserRegisterDTO;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Mapper mapper;

    public UserDTO register(UserRegisterDTO userDTO) {

        // TODO: Implement checks and validations for the user registration process


        User new_user = mapper.toEntity(userDTO);
        usersRepository.save(new_user);
        return mapper.toDTO(new_user);
    }
}
