package unq.pdes._5.g1.segui_tus_compras.model.dto.in.purchase;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PurchaseItemDto {
    @NotNull
    @NotEmpty
    private String productId;
    @NotNull
    @Positive
    private int amount;
}
