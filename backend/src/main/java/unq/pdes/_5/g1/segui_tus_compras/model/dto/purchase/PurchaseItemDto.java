package unq.pdes._5.g1.segui_tus_compras.model.dto.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PurchaseItemDto {
    @NotNull
    public String productId;
    @NotNull
    @Positive
    public Integer amount;
}
