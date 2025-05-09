package unq.pdes._5.g1.segui_tus_compras.model.dto;

import lombok.Getter;

@Getter
public class PagingDto {
    private int total;
    private int limit;
    private int offset;

    public PagingDto(int total, int limit, int offset) {
        this.total = total;
        this.limit = limit;
        this.offset = offset;
    }

    public PagingDto() {}
}
