package unq.pdes._5.g1.segui_tus_compras.model.dto.out.search;

import lombok.Getter;

@Getter
public class PagingDto {
    private int offset;
    private int limit;
    private int total;

    public PagingDto(int offset, int limit, int total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }

    public PagingDto() {}
}
