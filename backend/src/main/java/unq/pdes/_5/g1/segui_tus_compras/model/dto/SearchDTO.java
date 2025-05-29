package unq.pdes._5.g1.segui_tus_compras.model.dto;

import unq.pdes._5.g1.segui_tus_compras.model.product.Product;

import java.util.List;

public class SearchDTO {
    public PagingDto paging;
    public String query;
    public List<Product> results;

    public SearchDTO(PagingDto paging, String query, List<Product> results) {
        this.paging = paging;
        this.query = query;
        this.results = results;
    }
}
