package unq.pdes._5.g1.segui_tus_compras.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import unq.pdes._5.g1.segui_tus_compras.exception.product.ProductNotFoundException;
import unq.pdes._5.g1.segui_tus_compras.model.dto.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.service.external.MeLiApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MeLiApiService meLiApiService;

    @MockitoBean
    private ProductsRepository productsRepository;

    private final String TEST_PRODUCT_ID = "MLA123456789";
    private final String NON_EXISTENT_PRODUCT_ID = "MLA999999999";
    private final String INVALID_FORMAT_ID = "INVALID-123";


    @Test
    void testGetProductById() throws Exception {
        // Mock that repository does not contain the product (returns empty)
        when(productsRepository.findById(TEST_PRODUCT_ID)).thenReturn(Optional.empty());

        // Mock MeLiApiService to return mock product data
        ExternalProductDto mockExternalProduct = createMockExternalProductDto();
        when(meLiApiService.getProductById(TEST_PRODUCT_ID)).thenReturn(mockExternalProduct);

        // Mock that repository saves the product and returns it
        when(productsRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            return savedProduct;
        });

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + TEST_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TEST_PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test product description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFreeShipping").value(true));

        // Verify that the repository was called to save the product
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void testGetNonExistentProductById() throws Exception {
        // Mock repository to return empty (not found)
        when(productsRepository.findById(NON_EXISTENT_PRODUCT_ID)).thenReturn(Optional.empty());

        // Mock MeLiApiService to throw ProductNotFoundException when the product is not found
        when(meLiApiService.getProductById(NON_EXISTENT_PRODUCT_ID)).thenThrow(new ProductNotFoundException(NON_EXISTENT_PRODUCT_ID));

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + NON_EXISTENT_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Creates a mock ExternalProductDto object with all necessary fields populated
     */
    private ExternalProductDto createMockExternalProductDto() {
        ExternalProductDto productDto = new ExternalProductDto();
        productDto.id = TEST_PRODUCT_ID;
        productDto.name = "Test Product";

        // Set description
        ExternalProductDto.ShortDescriptionDto descriptionDto = new ExternalProductDto.ShortDescriptionDto();
        descriptionDto.content = "This is a test product description";
        productDto.description = descriptionDto;

        // Set attributes
        List<ExternalProductDto.AttributeDto> attributes = new ArrayList<>();
        ExternalProductDto.AttributeDto attr = new ExternalProductDto.AttributeDto();
        attr.id = "BRAND";
        attr.name = "Brand";
        attr.value = "Test Brand";
        attributes.add(attr);
        productDto.attributes = attributes;

        // Set pictures
        List<ExternalProductDto.PictureDto> pictures = new ArrayList<>();
        ExternalProductDto.PictureDto pic = new ExternalProductDto.PictureDto();
        pic.url = "http://example.com/image.jpg";
        pictures.add(pic);
        productDto.pictures = pictures;

        // Set buy box winner
        ExternalProductDto.BuyBoxWinnerDto buyBoxWinner = new ExternalProductDto.BuyBoxWinnerDto();
        buyBoxWinner.categoryId = "MLA1234";
        buyBoxWinner.sellerId = 12345L;
        buyBoxWinner.price = 80.0;
        buyBoxWinner.originalPrice = 100.0;

        // Set shipping
        ExternalProductDto.BuyBoxWinnerDto.ShippingDto shipping = new ExternalProductDto.BuyBoxWinnerDto.ShippingDto();
        shipping.freeShipping = true;
        buyBoxWinner.shipping = shipping;

        productDto.buyBoxWinner = buyBoxWinner;

        return productDto;
    }
}
