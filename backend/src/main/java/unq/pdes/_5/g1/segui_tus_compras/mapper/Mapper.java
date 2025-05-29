package unq.pdes._5.g1.segui_tus_compras.mapper;

import org.springframework.stereotype.Component;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.UserDTO;
import unq.pdes._5.g1.segui_tus_compras.model.dto.auth.RegisterData;

@Component
public class Mapper {
    public User toEntity(RegisterData userDTO) {
        return new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword()
        );
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getFirstName(),user.getLastName(),user.getEmail());
    }
}
