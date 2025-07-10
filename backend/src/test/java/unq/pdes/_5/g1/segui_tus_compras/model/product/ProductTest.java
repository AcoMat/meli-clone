package unq.pdes._5.g1.segui_tus_compras.model.product;

import org.junit.jupiter.api.Test;
import unq.pdes._5.g1.segui_tus_compras.model.dto.in.meli_api.ExternalProductDto;
import unq.pdes._5.g1.segui_tus_compras.model.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    /**
     * Helper method to create a sample external product DTO for testing
     */
    private ExternalProductDto createSampleExternalProductDto() {
        // Create a test ExternalProductDto
        ExternalProductDto dto = new ExternalProductDto();
        dto.id = "EXTERNAL-123";
        dto.name = "External Product";

        // Set description
        ExternalProductDto.ShortDescriptionDto descriptionDto = new ExternalProductDto.ShortDescriptionDto();
        descriptionDto.content = "External product description";
        dto.description = descriptionDto;

        // Set attributes
        ExternalProductDto.AttributeDto attributeDto = new ExternalProductDto.AttributeDto();
        attributeDto.id = "BRAND";
        attributeDto.name = "Brand";
        attributeDto.value = "Test Brand";
        dto.attributes = List.of(attributeDto);

        // Set pictures
        ExternalProductDto.PictureDto pictureDto = new ExternalProductDto.PictureDto();
        pictureDto.url = "http://test.com/image.jpg";
        dto.pictures = List.of(pictureDto);

        // Set buy box winner
        ExternalProductDto.BuyBoxWinnerDto buyBoxWinnerDto = new ExternalProductDto.BuyBoxWinnerDto();
        buyBoxWinnerDto.price = 90.0;
        buyBoxWinnerDto.originalPrice = 100.0;

        // Set shipping info
        ExternalProductDto.BuyBoxWinnerDto.ShippingDto shippingDto = new ExternalProductDto.BuyBoxWinnerDto.ShippingDto();
        shippingDto.freeShipping = true;
        buyBoxWinnerDto.shipping = shippingDto;

        dto.buyBoxWinner = buyBoxWinnerDto;

        return dto;
    }

    /**
     * Helper method to create a test user
     */
    private User createTestUser(String firstName, String lastName) {
        // Using empty email and password for simplicity
        return new User(firstName,lastName, "", "");
    }

    /**
     * Helper method to create a Product instance with custom values using the full constructor
     */
    private Product createTestProduct(String id, Double price, Integer discountPercentage) {
        // Since Product doesn't have setters, we need to use reflection to set values for testing
        ExternalProductDto dto = createSampleExternalProductDto();
        dto.id = id;
        dto.buyBoxWinner.originalPrice = price;
        if (discountPercentage != null) {
            dto.buyBoxWinner.price = price * (1 - discountPercentage / 100.0);
        }
        return new Product(dto);
    }

    @Test
    void emptyConstructor_ShouldInitializeEmptyCollections() {
        // Arrange & Act
        Product product = new Product();

        // Assert
        assertNotNull(product.getCommentaries());
        assertTrue(product.getCommentaries().isEmpty());

        assertNotNull(product.getReviews());
        assertTrue(product.getReviews().isEmpty());

        assertNotNull(product.getFavoritedBy());
        assertTrue(product.getFavoritedBy().isEmpty());
    }

    @Test
    void constructorWithExternalProductDto_ShouldMapAllPropertiesCorrectly() {
        // Arrange
        ExternalProductDto dto = createSampleExternalProductDto();

        // Act
        Product product = new Product(dto);

        // Assert
        assertEquals("EXTERNAL-123", product.getId());
        assertEquals("External Product", product.getName());
        assertEquals("External product description", product.getDescription());

        // Check attributes
        assertNotNull(product.getAttributes());
        assertEquals(1, product.getAttributes().size());
        assertEquals("BRAND", product.getAttributes().getFirst().getId());
        assertEquals("Brand", product.getAttributes().getFirst().getName());
        assertEquals("Test Brand", product.getAttributes().getFirst().getValue());

        // Check pictures
        assertNotNull(product.getPictures());
        assertEquals(1, product.getPictures().size());
        assertEquals("http://test.com/image.jpg", product.getPictures().getFirst());

        // Check price and discount
        assertEquals(100.0, product.getPrice());
        assertEquals(10, product.getPriceDiscountPercentage());

        // Check free shipping
        assertTrue(product.getIsFreeShipping());

        // Check commentaries initialization
        assertNotNull(product.getCommentaries());
        assertTrue(product.getCommentaries().isEmpty());
    }

    @Test
    void constructorWithNullValues_ShouldHandleNullsGracefully() {
        // Arrange
        ExternalProductDto nullDto = new ExternalProductDto();
        nullDto.id = "NULL-123";
        nullDto.name = "Test Null";
        nullDto.description = null;
        nullDto.attributes = null;
        nullDto.pictures = null;
        nullDto.buyBoxWinner = null;

        // Act
        Product product = new Product(nullDto);

        // Assert
        assertEquals("NULL-123", product.getId());
        assertEquals("Test Null", product.getName());
        assertNull(product.getDescription());
        assertNull(product.getAttributes());
        assertNull(product.getPictures());
        assertNull(product.getPrice());
        assertNull(product.getPriceDiscountPercentage());
        assertNull(product.getIsFreeShipping());
    }

    @Test
    void getPriceWithDiscountApplied_WithDiscount_ShouldReturnDiscountedPrice() {
        // Arrange
        Product product = createTestProduct("TEST-123", 100.0, 20);

        // Act
        Double discountedPrice = product.getPriceWithDiscountApplied();

        // Assert
        assertEquals(80.0, discountedPrice);
    }

    @Test
    void getPriceWithDiscountApplied_WithZeroDiscount_ShouldReturnOriginalPrice() {
        // Arrange
        Product product = createTestProduct("TEST-123", 100.0, 0);

        // Act
        Double result = product.getPriceWithDiscountApplied();

        // Assert
        assertEquals(100.0, result);
    }

    @Test
    void getPriceWithDiscountApplied_WithNullDiscount_ShouldReturnOriginalPrice() {
        // Arrange
        ExternalProductDto dto = createSampleExternalProductDto();
        dto.buyBoxWinner.price = dto.buyBoxWinner.originalPrice; // No discount
        Product product = new Product(dto);

        // Act
        Double result = product.getPriceWithDiscountApplied();

        // Assert
        assertEquals(100.0, result);
    }

    @Test
    void getPriceWithDiscountApplied_WithNullPrice_ShouldReturnNull() {
        // Arrange
        ExternalProductDto dto = createSampleExternalProductDto();
        dto.buyBoxWinner.originalPrice = null;
        Product product = new Product(dto);

        // Act
        Double result = product.getPriceWithDiscountApplied();

        // Assert
        assertNull(result);
    }

    @Test
    void addComment_ShouldAddCommentToCollection() {
        // Arrange
        Product product = new Product();
        User user = createTestUser("Test", "User");
        Commentary comment = new Commentary(user, product, "Great product!");

        // Act
        product.addComment(comment);

        // Assert
        assertEquals(1, product.getCommentaries().size());
        assertEquals("Great product!", product.getCommentaries().getFirst().getComment());
        assertEquals(user, product.getCommentaries().getFirst().getUser());
    }

    @Test
    void addReview_WithNewReview_ShouldAddToCollection() {
        // Arrange
        Product product = new Product();
        User user = createTestUser("Test", "User");
        Review review = new Review(product, user, 4, "Good product");

        // Act
        product.addReview(review);

        // Assert
        assertEquals(1, product.getReviews().size());
        assertEquals(4, product.getReviews().getFirst().getRating());
        assertEquals("Good product", product.getReviews().getFirst().getComment());
    }

    @Test
    void addReview_WithExistingUserReview_ShouldReplaceOldReview() {
        // Arrange
        Product product = new Product();
        User user = createTestUser("Test", "User");
        // Set non-null IDs using reflection
        try {
            java.lang.reflect.Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Review firstReview = new Review(product, user, 4, "Good product");
        product.addReview(firstReview);

        Review updatedReview = new Review(product, user, 5, "Updated opinion: Excellent product");

        // Act
        product.addReview(updatedReview);

        // Assert
        assertEquals(1, product.getReviews().size());
        assertEquals(5, product.getReviews().getFirst().getRating());
        assertEquals("Updated opinion: Excellent product", product.getReviews().getFirst().getComment());
    }

    @Test
    void addReview_FromDifferentUsers_ShouldAddMultipleReviews() {
        // Arrange
        Product product = new Product();
        User firstUser = createTestUser("First", "User");
        User secondUser = createTestUser("Second", "User");

        // Set non-null IDs using reflection
        try {
            java.lang.reflect.Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(firstUser, 1L);
            idField.set(secondUser, 2L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Review firstReview = new Review(product, firstUser, 4, "Review from first user");
        Review secondReview = new Review(product, secondUser, 5, "Review from second user");

        // Act
        product.addReview(firstReview);
        product.addReview(secondReview);

        // Assert
        assertEquals(2, product.getReviews().size());
    }
}