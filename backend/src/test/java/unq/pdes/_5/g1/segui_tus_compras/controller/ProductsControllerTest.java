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
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ApiSearchDto;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.product.Commentary;
import unq.pdes._5.g1.segui_tus_compras.model.product.Product;
import unq.pdes._5.g1.segui_tus_compras.model.product.Review;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.Purchase;
import unq.pdes._5.g1.segui_tus_compras.model.purchase.PurchaseItem;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;
import unq.pdes._5.g1.segui_tus_compras.repository.ProductsRepository;
import unq.pdes._5.g1.segui_tus_compras.repository.UsersRepository;
import unq.pdes._5.g1.segui_tus_compras.security.JwtTokenProvider;
import unq.pdes._5.g1.segui_tus_compras.service.external.MeLiApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;
    @MockitoBean
    private ProductsRepository productsRepository;
    @MockitoBean
    private UsersRepository usersRepository;
    @MockitoBean
    private MeLiApiService meLiApiService;


    @Test
    void testGetProductById() throws Exception {
        ExternalProductDto mockedProduct = createMockExternalProductDto();
        // Mock that repository does not contain the product (returns empty)
        when(productsRepository.findById(mockedProduct.getId())).thenReturn(Optional.empty());
        // Mock MeLiApiService to return mock product data
        when(meLiApiService.getProductById(mockedProduct.getId())).thenReturn(mockedProduct);

        // Mock that repository saves the product and returns it
        when(productsRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.<Product>getArgument(0));

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + mockedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(mockedProduct.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This is a test product description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isFreeShipping").value(true));

        // Verify that the repository was called to save the product
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void testGetNonExistentProductById() throws Exception {
        String NON_EXISTENT_PRODUCT_ID = "MLA999999999";
        // Mock repository to return empty (not found)
        when(productsRepository.findById(NON_EXISTENT_PRODUCT_ID)).thenReturn(Optional.empty());

        // Mock MeLiApiService to throw ProductNotFoundException when the product is not found
        when(meLiApiService.getProductById(NON_EXISTENT_PRODUCT_ID)).thenThrow(new ProductNotFoundException(NON_EXISTENT_PRODUCT_ID));

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + NON_EXISTENT_PRODUCT_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetCommentsFromProduct() throws Exception {
        Product mockedProduct = new Product(createMockExternalProductDto());
        User user = new User("John","Doe","john@email.com","securePass");
        mockedProduct.addComment(new Commentary(user, mockedProduct, "Great product!"));
        when(productsRepository.findById(mockedProduct.getId())).thenReturn(Optional.of(mockedProduct));

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + mockedProduct.getId() + "/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Great product!"));
    }


    @Test
    void testPostCommentToProduct() throws Exception {
        String testComment = "This is a test comment";
        Product product = new Product(createMockExternalProductDto());

        String userToken = "testUserToken";
        Long userId = 1L;
        User user = new User("John","Doe","john@email.com","securePass");


        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productsRepository.existsById(product.getId())).thenReturn(true);
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken)).thenReturn(userId);

        // Perform request to add comment with Authorization header
        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"comment\":\"" + testComment + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Comment added successfully"));

        // Verify that the repository was called to save the product
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void testGetReviewsFromProduct() throws Exception {
        Product product = new Product(createMockExternalProductDto());
        User user = new User("John","Doe","john@email.com","securePass");
        product.addReview(new Review(product, user, 5, "Excellent quality!"));

        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/" + product.getId() + "/reviews")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Excellent quality!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(5));
    }

    @Test
    void testPostReviewToProduct() throws Exception {
        Product product = new Product(createMockExternalProductDto());

        String userToken = "testUserToken";
        Long userId = 1L;
        User user = new User("John","Doe","john@email.com","securePass");

        // Create purchase with one item using the product
        PurchaseItem purchaseItem = new PurchaseItem(product, 1);
        List<PurchaseItem> purchaseItems = List.of(purchaseItem);
        Purchase purchase = new Purchase(user, purchaseItems);
        user.addPurchase(purchase);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productsRepository.existsById(product.getId())).thenReturn(true);
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken)).thenReturn(userId);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"review\": \"test\", \"rating\": 5}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review added successfully"));

        // Verify that the repository was called to save the product
        verify(productsRepository).save(any(Product.class));
    }

    @Test
    void testPostMultipleReviewsToProduct() throws Exception {
        Product product = new Product(createMockExternalProductDto());

        String userToken = "testUserToken";
        Long userId = 1L;
        User user = spy(new User("John", "Doe", "john@email.com", "securePass"));
        doReturn(1L).when(user).getId();
        // Create purchase with one item using the produc
        PurchaseItem purchaseItem = new PurchaseItem(product, 1);
        List<PurchaseItem> purchaseItems = List.of(purchaseItem);
        Purchase purchase = new Purchase(user, purchaseItems);
        user.addPurchase(purchase);

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productsRepository.existsById(product.getId())).thenReturn(true);
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken)).thenReturn(userId);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"review\": \"test\", \"rating\": 5}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review added successfully"));

        // Verify that the repository was called to save the product
        verify(productsRepository).save(any(Product.class));

        // Perform another request to add a different review
        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"review\": \"another test\", \"rating\": 4}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review added successfully"));
        // Verify that the repository was called to save the product again
        verify(productsRepository, times(2)).save(any(Product.class));
        // Verify that the product has only the last review added
        List<Review> reviews = product.getReviews();
        assert reviews.size() == 1 : "Product should have only the last review made by the user";
    }

    @Test
    void testPostReviewToProductWithoutPurchase() throws Exception {
        Product product = new Product(createMockExternalProductDto());

        String userToken = "testUserToken";
        Long userId = 1L;
        User user = new User("John","Doe","john@email.com","securePass");

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productsRepository.existsById(product.getId())).thenReturn(true);
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken)).thenReturn(userId);

        // Perform request to add comment with Authorization header
        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"review\": \"test\", \"rating\": 5}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("You need to buy the product before reviewing it"));

        // Verify that the repository was not called to save the product
        verify(productsRepository, never()).save(any(Product.class));
    }

    @Test
    void testMultipleUsersReviewingSameProduct() throws Exception {
        Product product = new Product(createMockExternalProductDto());
        when(productsRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productsRepository.existsById(product.getId())).thenReturn(true);

        String userToken = "testUserToken";
        Long userId = 1L;
        User user = spy(new User("John", "Doe", "john@email.com", "securePass"));
        doReturn(1L).when(user).getId();
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken)).thenReturn(userId);

        String userToken2 = "testUserToken2";
        Long userId2 = 2L;
        User user2 = spy(new User("John", "Doe", "john@email.com", "securePass"));
        doReturn(1L).when(user).getId();
        when(usersRepository.findById(userId2)).thenReturn(Optional.of(user2));
        when(jwtTokenProvider.validateTokenAndGetUserId(userToken2)).thenReturn(userId2);

        // Create purchase with one item using the produc
        PurchaseItem purchaseItem = new PurchaseItem(product, 1);
        List<PurchaseItem> purchaseItems = List.of(purchaseItem);
        Purchase purchase = new Purchase(user, purchaseItems);

        user.addPurchase(purchase);
        user2.addPurchase(purchase);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken)
                        .content("{\"review\": \"review\", \"rating\": 5}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review added successfully"));

        // Perform another request to add a different review
        mockMvc.perform(MockMvcRequestBuilders.post("/products/" + product.getId() + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", userToken2)
                        .content("{\"review\": \"another review from another user\", \"rating\": 4}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Review added successfully"));

        // Verify that the repository was called to save the product
        verify(productsRepository, times(2)).save(any(Product.class));
        // Verify that the product has both reviews added
        List<Review> reviews = product.getReviews();
        assert reviews.size() == 2 : "Product should have both reviews made by different users";
    }

    @Test
    void testSearchProductsByName() throws Exception {
        String searchQuery = "Products";

        // Products
        Product product1 = new Product(createMockExternalProductDto());
        Product product2 = new Product(createMockExternalProductDto());
        Product product3 = new Product(createMockExternalProductDto());

        // Arrange: Mock the MeLiApiService to return a mock ApiSearchDto
        ApiSearchDto mockApiSearchDto = new ApiSearchDto();
        ApiSearchDto.SearchResultDto mockSearchResultDto1 = new ApiSearchDto.SearchResultDto();
        mockSearchResultDto1.id = product1.getId();
        ApiSearchDto.SearchResultDto mockSearchResultDto2 = new ApiSearchDto.SearchResultDto();
        mockSearchResultDto2.id = product2.getId();
        ApiSearchDto.SearchResultDto mockSearchResultDto3 = new ApiSearchDto.SearchResultDto();
        mockSearchResultDto3.id = product3.getId();
        mockApiSearchDto.results = List.of(mockSearchResultDto1, mockSearchResultDto2, mockSearchResultDto3);
        mockApiSearchDto.keywords = searchQuery;
        ApiSearchDto.PagingDto mockPagingDto = new ApiSearchDto.PagingDto();
        mockPagingDto.total = 4; // Mock total results
        mockPagingDto.offset = 0;
        mockPagingDto.limit = 10;
        mockApiSearchDto.paging = mockPagingDto;

        // Mock the MeLiApiService to return the mock ApiSearchDto when search is called
        when(meLiApiService.search(searchQuery,0,10)).thenReturn(mockApiSearchDto);

        // Mock the ProductsRepository to return Product objects for each search result
        when(productsRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productsRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
        when(productsRepository.findById(product3.getId())).thenReturn(Optional.of(product3));

        // Perform the search request
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("q", searchQuery)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Validate that the results contain the expected products
                .andExpect(MockMvcResultMatchers.jsonPath("$.query").value(searchQuery))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].id").value(product1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[1].id").value(product2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[2].id").value(product3.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.total").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.offset").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paging.limit").value(10));
        // Verify that the MeLiApiService was called with the correct parameters
        verify(meLiApiService).search(searchQuery, 0, 10);
    }


    private ExternalProductDto createMockExternalProductDto() {
        String testProduct = "MLA" + new Random().ints(9, 'A', 'Z' + 1)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        ExternalProductDto productDto = new ExternalProductDto();
        productDto.id = testProduct;
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
