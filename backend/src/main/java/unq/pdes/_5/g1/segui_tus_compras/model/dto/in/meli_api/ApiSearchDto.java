package unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiSearchDto {
    public String keywords;
    public PagingDto paging;
    public List<SearchResultDto> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PagingDto {
        public int total;
        public int limit;
        public int offset;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SearchResultDto {
        public String id;
    }
}
