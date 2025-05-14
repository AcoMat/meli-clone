package unq.pdes._5.g1.segui_tus_compras.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewDto {

    @NotBlank(message = "Review cannot be blank")
    @Size(max = 500, message = "Review cannot exceed 500 characters")
    public String review;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    public int rating;
}
