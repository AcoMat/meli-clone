package unq.pdes._5.g1.segui_tus_compras.model.dto.api;

import java.util.List;

public class ExternalProductDto {
    public String id;
    public String name;
    public String description;

    public List<AttributeDto> attributes;
    public List<PictureDto> pictures;
    public BuyBoxWinnerDto buyBoxWinner;

    public static class AttributeDto {
        public String id;
        public String name;
        public String value;
    }

    public static class PictureDto {
        public String url;
    }

    public static class BuyBoxWinnerDto {
        public String categoryId;
        public Long sellerId;
        public Double price;
        public Boolean freeShipping;
        public Double originalPrice;

        public static class ShippingDto {
            public Boolean freeShipping;
        }
    }
}
