package unq.pdes._5.g1.segui_tus_compras.model.dto.out.user;

import unq.pdes._5.g1.segui_tus_compras.model.user.User;

public class BasicUserDto {
    public final Long id;
    public final String firstName;
    public final String lastName;
    public final String email;

    public BasicUserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }

}
