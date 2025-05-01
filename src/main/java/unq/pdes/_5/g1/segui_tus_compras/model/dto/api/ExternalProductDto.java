package unq.pdes._5.g1.segui_tus_compras.model.dto.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExternalProductDto {
    public String id;
    public String name;
    @JsonProperty("short_description")
    public ShortDescriptionDto description;

    public List<AttributeDto> attributes;
    public List<PictureDto> pictures;
    @JsonProperty("buy_box_winner")
    public BuyBoxWinnerDto buyBoxWinner;

    @Data
    public static class ShortDescriptionDto {
        public String content;
    }

    @Data
    public static class AttributeDto {
        public String id;
        public String name;
        @JsonProperty("value_name")
        public String value;
    }

    @Data
    public static class PictureDto {
        public String url;
    }

    @Data
    public static class BuyBoxWinnerDto {
        @JsonProperty("category_id")
        public String categoryId;
        @JsonProperty("seller_id")
        public Long sellerId;
        public Double price;
        public ShippingDto shipping;
        @JsonProperty("original_price")
        public Double originalPrice;

        @Data
        public static class ShippingDto {
            @JsonProperty("free_shipping")
            public Boolean freeShipping;
        }
    }
}