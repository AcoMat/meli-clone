package unq.pdes._5.g1.segui_tus_compras.mapper;

import org.springframework.stereotype.Component;
import unq.pdes._5.g1.segui_tus_compras.model.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.UserRegisterDTO;

@Component
public class Mapper {
    public User toEntity(UserRegisterDTO userDTO) {
        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword());
    }

    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.firstName = user.getFirstName();
        userDTO.lastName = user.getLastName();
        userDTO.email = user.getEmail();
        return userDTO;
    }
}
