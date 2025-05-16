package unq.pdes._5.g1.segui_tus_compras.model.dto.purchase;

import jakarta.validation.constraints.Size;
import java.util.List;

public class PurchaseDto {
    @Size(min = 1, max = 100)
    public List<PurchaseItemDto> items;
}
