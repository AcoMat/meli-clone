package unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api;

import lombok.Data;

import java.util.List;

@Data
public class ApiSearchDto {
    public String keywords;
    public PagingDto paging;
    public List<SearchResultDto> results;

    @Data
    public static class PagingDto {
        public int total;
        public int limit;
        public int offset;
    }

    @Data
    public static class SearchResultDto {
        public String id;
    }
}
