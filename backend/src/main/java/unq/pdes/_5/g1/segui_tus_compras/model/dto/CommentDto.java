package unq.pdes._5.g1.segui_tus_compras.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommentDto {
    
    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 255, message = "Comment cannot exceed 255 characters")
    public String comment;
}
