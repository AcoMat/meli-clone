package unq.pdes._5.g1.segui_tus_compras.model.dto.purchase;

import org.hibernate.validator.constraints.Length;

import java.util.List;

public class PurchaseDto {
    @Length(min = 1, max = 50)
    public List<PurchaseItemDto> items;
}
