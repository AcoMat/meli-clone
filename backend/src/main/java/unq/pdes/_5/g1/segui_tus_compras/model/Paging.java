package unq.pdes._5.g1.segui_tus_compras.model;

import lombok.Getter;

@Getter
public class Paging {
    private int total;
    private int limit;
    private int offset;

    public Paging(int total, int limit, int offset) {
        this.total = total;
        this.limit = limit;
        this.offset = offset;
    }

    public Paging () {}
}
