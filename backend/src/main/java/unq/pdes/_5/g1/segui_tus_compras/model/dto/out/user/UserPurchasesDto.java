package unq.pdes._5.g1.segui_tus_compras.model.dto.out.user;

import unq.pdes._5.g1.segui_tus_compras.model.user.User;

public class UserPurchasesDto {
    public Long userId;
    public String fullName;
    public Long purchaseCount;

    public UserPurchasesDto(User user, Long purchaseCount) {
        this.userId = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.purchaseCount = purchaseCount;
    }
}
